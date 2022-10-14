package top.reminisce.coolnetblogcore.config.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.reminisce.coolnetblogcore.handler.springsecurity.JwtAuthenticationPreferentialFilter;

/**
 * 用于设置Spring Security相关的配置类
 * @author BlueSky
 * @date 2022/10/10
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationPreferentialFilter jwtAuthenticationPreferentialFilter;

    /**
     * 配置Spring Security默认的登录密码加密和效验方式，取代明文存储。
     * @return PasswordEncoder Bean
     */
    @Bean
    public PasswordEncoder passwordEncoderSetting(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 通常用于 配置允许放行的静态资源路径
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        // 放行静态资源
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * 通常用于 配置允许放行的请求接口，以及过滤器链
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        /*
        * 配置请求身份规则:
        * /admin/login 需要匿名登录
        * /admin/ 需要合法token验证
        * 其余前台接口任何人皆可请求
        */
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers("/admin/login").anonymous().and()
            .authorizeRequests().antMatchers("/admin/**").authenticated().and()
            .authorizeRequests().antMatchers("/**").permitAll();
        // 将自定义JWT认证过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationPreferentialFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 获取AuthenticationManager Bean
     * @return spring security用于处理authenticate身份验证的实现类
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
