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
