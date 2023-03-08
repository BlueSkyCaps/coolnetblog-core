package top.reminisce.coolnetblogcore.handler.springsecurity;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ACCOUNT_TOKEN_NULL_TIPS;
import static top.reminisce.coolnetblogcore.handler.springsecurity.JwtAuthenticationPreferentialFilter.extractedExceptionDispatcher;

/**

 */

/**
 * 重写Http403ForbiddenEntryPoint，用于Spring Security中配置authenticationEntryPoint的handler。<br/>
 * Spring Security拒绝未经验证的uri访问，并返回Access denied结果。此结果位于Spring Security过滤器链中
 * 返回，因此尚未达到Controller层无法被自定义全局异常捕获。重写commence方法，转发给控制器用于返回全局自定义响应体。
 * @author BlueSky
 * @date 2023/3/8
 */
public class SimpleAccessDeniedEntryPoint extends Http403ForbiddenEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) {

        try {
            extractedExceptionDispatcher(request, ACCOUNT_TOKEN_NULL_TIPS, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
