package top.reminisce.coolnetblogcore.config.springsecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;

import java.util.Collection;

/**
 * 此类实现实现自Spring Security的UserDetails接口，用于返回封装的SysAdmin配置信息给Spring Security做后续过滤器的判断
 * @author BlueSky
 * @date 2022/10/10
 */
public class LoginUserInfo implements UserDetails {
    private final CoreSysAdmin sysAdmin;
    private final String carryToken;

    public LoginUserInfo(CoreSysAdmin sysAdmin, String carryToken) {

        this.sysAdmin = sysAdmin;
        this.carryToken = carryToken;
    }


    public CoreSysAdmin getSysAdmin() {
        return sysAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return sysAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return sysAdmin.getAccountName();

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getCarryToken() {
        return carryToken;
    }
}
