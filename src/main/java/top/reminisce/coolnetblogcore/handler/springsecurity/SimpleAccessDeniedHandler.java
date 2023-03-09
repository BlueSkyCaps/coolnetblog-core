package top.reminisce.coolnetblogcore.handler.springsecurity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ACCOUNT_TOKEN_ANON_TIPS;
import static top.reminisce.coolnetblogcore.handler.springsecurity.JwtAuthenticationPreferentialFilter.extractedExceptionDispatcher;

/**
 * 实现AccessDeniedHandler，用于Spring Security中配置accessDeniedHandler的handler。<br/>
 * Spring Security拒绝未经验证的uri访问，并返回Access denied结果。此结果位于Spring Security过滤器链中
 * 返回，因此尚未达到Controller层无法被自定义全局异常捕获。重写handle方法，转发给控制器用于返回全局自定义响应体。<br/><br/>
 * <i>此handler拒绝本该匿名访问的接口却使用有效的token访问(非匿名)，返回自定义响应，如/admin/login登录接口<i/>
 * @author BlueSky
 * @date 2023/3/9
 */
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        try {
            extractedExceptionDispatcher(request, ACCOUNT_TOKEN_ANON_TIPS, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
