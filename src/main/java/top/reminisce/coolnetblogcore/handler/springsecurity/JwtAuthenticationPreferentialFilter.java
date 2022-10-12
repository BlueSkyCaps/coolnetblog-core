package top.reminisce.coolnetblogcore.handler.springsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.reminisce.coolnetblogcore.common.CommonGlobalRef;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.util.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BlueSky
 * @date 2022/10/12
 */
public class JwtAuthenticationPreferentialFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        // 请求头中不包含token 放行。若是请求允许的接口，后续security过滤器处理响应；若是后台接口，后续处理器拦截抛出
        if (! StringUtils.hasText(token)){
            doFilter(request, response, filterChain);
        }
        /* 解析jwt 验证有效性 */
        // 获取账户名

        try {
            String accountName = JwtUtils.parseToken(JwtUtils.AVAILABLE_JWT_SECRET_KEY, token).getBody().getId();
        } catch (Exception e) {
            throw new BlogAccountNotRightExceptionTips(CommonGlobalRef.ACCOUNT_AT_NOT_RIGHT_TIPS);
        }
    }
}
