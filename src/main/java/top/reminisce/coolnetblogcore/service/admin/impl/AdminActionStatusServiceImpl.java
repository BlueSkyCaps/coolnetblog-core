package top.reminisce.coolnetblogcore.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.common.TimestampOffsetActually;
import top.reminisce.coolnetblogcore.config.springsecurity.LoginUserInfo;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.pojo.dto.LoginDto;
import top.reminisce.coolnetblogcore.pojo.dto.ResetPasswordDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.service.admin.AdminActionStatusService;
import top.reminisce.coolnetblogcore.service.admin.AdminQueryService;
import top.reminisce.coolnetblogcore.service.admin.AdminSaveService;
import top.reminisce.coolnetblogcore.util.JwtUtils;
import top.reminisce.coolnetblogcore.util.SecurityPasswordUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.util.Objects;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ACCOUNT_USER_INVALID_TIPS;
import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.REDIS_LOGOUT_KEY_NAME;

/**
 * @author BlueSky
 * @date 2022/10/10
 */
@Service
public class AdminActionStatusServiceImpl implements AdminActionStatusService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AdminSaveService adminSaveService;
    @Qualifier("adminQueryServiceImpl")
    @Autowired
    private AdminQueryService adminQueryService;
    @Override
    public Object loginAction(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getAccountName(), loginDto.getPassword());
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BlogAccountNotRightExceptionTips(ACCOUNT_USER_INVALID_TIPS);
        }
        if (Objects.isNull(authenticate)){
            throw new BlogAccountNotRightExceptionTips(ACCOUNT_USER_INVALID_TIPS);
        }

        // ?????????????????????????????????UserDetails?????????????????????SysAdmin?????????LoginUserInfo
        LoginUserInfo principal = (LoginUserInfo)authenticate.getPrincipal();
        String accountName = principal.getUsername();
        // ?????????????????????????????????????????????????????????
        this.stringRedisTemplate.opsForSet().remove(REDIS_LOGOUT_KEY_NAME, accountName);
        /* ?????????????????????????????????????????????jwt ??????????????? ???token??????????????????*/
        // ??????token???id?????????????????????
        return JwtUtils.createToken(JwtUtils.AVAILABLE_JWT_SECRET_KEY,
            accountName, accountName,
            TimeUtils.timeLongAdd(TimestampOffsetActually.ONE_DAY.value()));
    }


    @Override
    public Object logoutAction() {
        // ?????????SecurityContext???????????????????????????
        LoginUserInfo principal = (LoginUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // ?????????????????? ??????????????????????????? ?????????????????????????????????token????????????????????????
        this.stringRedisTemplate.opsForSet().add(REDIS_LOGOUT_KEY_NAME, principal.getUsername());
        return true;
    }

    @Override
    public Object resetAction(ResetPasswordDto resetPasswordDto) {
        CoreSysAdmin sysAdmin = ((AdminQueryServiceImpl)(this.adminQueryService)).getSysAdmin(resetPasswordDto.getAccountName());
        // ??????????????????????????????
        sysAdmin.setAccountName(resetPasswordDto.getNewAccountName());
        String genNewPassword = SecurityPasswordUtils.genBcryptPassword(resetPasswordDto.getPassword());
        sysAdmin.setPassword(genNewPassword);
        // ??????admin???????????????????????????
        this.adminSaveService.saveSysAdmin(sysAdmin);
        // ???????????? ????????????
        this.logoutAction();
        return true;
    }
}
