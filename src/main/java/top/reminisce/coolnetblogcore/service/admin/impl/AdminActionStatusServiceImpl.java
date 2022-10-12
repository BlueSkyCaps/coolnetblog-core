package top.reminisce.coolnetblogcore.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.common.TimestampOffsetActually;
import top.reminisce.coolnetblogcore.config.springsecurity.LoginUserInfo;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.pojo.dto.LoginDto;
import top.reminisce.coolnetblogcore.service.admin.AdminActionStatusService;
import top.reminisce.coolnetblogcore.util.JwtUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.util.Objects;

/**
 * @author BlueSky
 * @date 2022/10/10
 */
@Service
public class AdminActionStatusServiceImpl implements AdminActionStatusService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public Object loginAction(LoginDto loginDto) {
        final String notRightTip = "账户或密码错误";
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getAccountName(), loginDto.getPassword());
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BlogAccountNotRightExceptionTips(notRightTip);
        }
        if (Objects.isNull(authenticate)){
            throw new BlogAccountNotRightExceptionTips(notRightTip);
        }
        // 获取正确的已验证通过的UserDetails对象，即包裹着SysAdmin实体的LoginUserInfo
        LoginUserInfo principal = (LoginUserInfo)authenticate.getPrincipal();
        /* 开始为当前登陆成功的管理员生成jwt 并封装数据 将token返回给客户端*/
        String accountName = principal.getUsername();
        // 创建token，id为管理员账户名
        return JwtUtils.createToken(JwtUtils.AVAILABLE_JWT_SECRET_KEY,
            accountName, accountName,
            TimeUtils.timeLongAdd(TimestampOffsetActually.ONE_DAY.value()));
    }

    @Override
    public Object logoutAction() {
        return null;
    }

    @Override
    public Object resetAction(LoginDto loginDto) {
        return null;
    }
}
