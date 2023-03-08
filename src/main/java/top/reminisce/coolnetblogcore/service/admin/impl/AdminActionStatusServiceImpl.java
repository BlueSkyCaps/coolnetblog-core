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

        // 获取正确的已验证通过的UserDetails对象，即包裹着SysAdmin实体的LoginUserInfo
        LoginUserInfo principal = (LoginUserInfo)authenticate.getPrincipal();
        String accountName = principal.getUsername();
        // 从缓存的注销集合中移除此账户名，若有。
        this.stringRedisTemplate.opsForSet().remove(REDIS_LOGOUT_KEY_NAME, accountName);
        /* 开始为当前登陆成功的管理员生成jwt 并封装数据 将token返回给客户端*/
        // 创建token，id为管理员账户名
        return JwtUtils.createToken(JwtUtils.AVAILABLE_JWT_SECRET_KEY,
            accountName, accountName,
            TimeUtils.timeLongAdd(TimestampOffsetActually.ONE_DAY.value()));
    }


    @Override
    public Object logoutAction() {
        // 从当前SecurityContext获取当前的用户信息
        LoginUserInfo principal = (LoginUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 添加到缓存中 表示此用户已经注销 后续请求即使使用有效的token，仍需要重新登录
        this.stringRedisTemplate.opsForSet().add(REDIS_LOGOUT_KEY_NAME, principal.getUsername());
        return true;
    }



    @Override
    public Object resetAction(ResetPasswordDto resetPasswordDto) {
        CoreSysAdmin sysAdmin = ((AdminQueryServiceImpl)(this.adminQueryService))
            .getSysAdmin(resetPasswordDto.getAccountName());
        if (Objects.isNull(sysAdmin)){
            // 没有此原账户名
            throw new BlogAccountNotRightExceptionTips(ACCOUNT_USER_INVALID_TIPS);
        }
        if (! SecurityPasswordUtils.passwordMatch(resetPasswordDto.getPassword(), sysAdmin.getPassword())){
            // 原密码错误
            throw new BlogAccountNotRightExceptionTips(ACCOUNT_USER_INVALID_TIPS);
        }
        // 设置新的账户名和密码
        sysAdmin.setAccountName(resetPasswordDto.getNewAccountName());
        String genNewPassword = SecurityPasswordUtils.genBcryptPassword(resetPasswordDto.getNewPassword());
        sysAdmin.setPassword(genNewPassword);
        // 调用admin服务层进行最终保存
        this.adminSaveService.saveSysAdmin(sysAdmin);

        // 添加到缓存中 表示此 原用户 已经注销，后续请求即使用有效的token，仍需要重新登录
        this.stringRedisTemplate.opsForSet().add(REDIS_LOGOUT_KEY_NAME, resetPasswordDto.getAccountName());
        return true;
    }
}
