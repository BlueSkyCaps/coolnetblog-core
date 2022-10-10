package top.reminisce.coolnetblogcore.service.home.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.pojo.LoginUserInfo;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeQueryService;

/**
 * 此类实现自Spring Security的UserDetailsService接口
 * @author BlueSky
 * @date 2022/10/10
 */
public class LoginSpringSecurityServiceImpl extends AbstractHomeQueryService implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if  (ObjectUtils.isEmpty(username.trim())){
            throw new BlogAccountNotRightExceptionTips("请输入用户名！");
        }
        CoreSysAdmin sysAdmin = super.getSetting(username);
        if (ObjectUtils.isEmpty(sysAdmin)){
            throw new BlogAccountNotRightExceptionTips("用户名或密码错误！");
        }
        //TODO 根据用户查询权限信息 添加到LoginUser中

        //封装成UserDetails对象返回给后续spring security过滤器
        return new LoginUserInfo(sysAdmin);
    }
}
