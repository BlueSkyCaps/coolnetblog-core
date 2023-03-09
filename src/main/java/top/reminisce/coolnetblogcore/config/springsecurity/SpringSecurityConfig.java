package top.reminisce.coolnetblogcore.config.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.reminisce.coolnetblogcore.handler.springsecurity.JwtAuthenticationPreferentialFilter;
import top.reminisce.coolnetblogcore.handler.springsecurity.SimpleAccessDeniedEntryPoint;
import top.reminisce.coolnetblogcore.handler.springsecurity.SimpleAccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ACCOUNT_TOKEN_NULL_TIPS;
import static top.reminisce.coolnetblogcore.handler.springsecurity.JwtAuthenticationPreferentialFilter.extractedExceptionDispatcher;

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
            .authorizeRequests().antMatchers("/admin/login", "/admin/reset").anonymous().and()
            .authorizeRequests().antMatchers("/admin/**").authenticated().and()
            .authorizeRequests().antMatchers("/**").permitAll();
        /*
        * 配置403权限错误 捕获返回全局的异常响应体。
        * 此SimpleAccessDeniedHandler实现AccessDeniedHandler接口
        * 此SimpleAccessDeniedEntryPoint身份验证入口点重写默认的Http403ForbiddenEntryPoint
        *  */
        http.exceptionHandling()
            .accessDeniedHandler(new SimpleAccessDeniedHandler())
            .authenticationEntryPoint(new SimpleAccessDeniedEntryPoint());
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
