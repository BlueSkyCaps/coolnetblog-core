package top.reminisce.coolnetblogcore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用于设置Spring Security相关的配置类
 * @author BlueSky
 * @date 2022/10/10
 */
@Configuration
public class SpringSecurityConfig {
    /**
     * 配置Spring Security默认的登录密码加密和效验方式，取代明文存储。
     * @return PasswordEncoder Bean
     */
    @Bean
    public PasswordEncoder passwordEncoderSetting(){
        return new BCryptPasswordEncoder();
    }
}
