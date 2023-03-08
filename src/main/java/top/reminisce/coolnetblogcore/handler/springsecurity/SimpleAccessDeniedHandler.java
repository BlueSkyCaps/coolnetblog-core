package top.reminisce.coolnetblogcore.handler.springsecurity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ACCOUNT_TOKEN_NULL_TIPS;
import static top.reminisce.coolnetblogcore.handler.springsecurity.JwtAuthenticationPreferentialFilter.extractedExceptionDispatcher;

/**
 * @author BlueSky
 * @date 2023/3/8
 */
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        extractedExceptionDispatcher(request, ACCOUNT_TOKEN_NULL_TIPS, response);
    }
}
