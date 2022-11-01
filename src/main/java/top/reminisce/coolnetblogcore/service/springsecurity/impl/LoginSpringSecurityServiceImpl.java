package top.reminisce.coolnetblogcore.service.springsecurity.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.config.springsecurity.LoginUserInfo;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeQueryService;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ACCOUNT_USER_INVALID_TIPS;

/**
 * 此类实现自Spring Security的UserDetailsService接口
 * @author BlueSky
 * @date 2022/10/10
 */
@Service
public class LoginSpringSecurityServiceImpl extends AbstractHomeQueryService implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CoreSysAdmin sysAdmin = super.getSysAdmin(username);
        if (ObjectUtils.isEmpty(sysAdmin)){
            throw new BlogAccountNotRightExceptionTips(ACCOUNT_USER_INVALID_TIPS);
        }

        //封装成UserDetails对象返回给后续spring security过滤器
        return new LoginUserInfo(sysAdmin);
    }
}
