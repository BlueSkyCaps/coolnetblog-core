package top.reminisce.coolnetblogcore.handler.springsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.reminisce.coolnetblogcore.common.CommonGlobalRef;
import top.reminisce.coolnetblogcore.config.springsecurity.LoginUserInfo;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.service.admin.AdminQueryService;
import top.reminisce.coolnetblogcore.service.admin.impl.AdminQueryServiceImpl;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeQueryService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;
import top.reminisce.coolnetblogcore.util.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当请求后台接口时，需要验证token的过滤拦截器。此过滤器在当前请求线程中应该率先被执行。
 * @author BlueSky
 * @date 2022/10/12
 */
@Component
public class JwtAuthenticationPreferentialFilter extends OncePerRequestFilter {
    @Autowired
    private AdminQueryService adminQueryService;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        // 请求头中不包含token 放行。若是请求允许的接口，后续security过滤器处理响应；若是后台接口，后续处理器拦截抛出
        if (!StringUtils.hasText(token)) {
            doFilter(request, response, filterChain);
        }
        /* 解析jwt 验证有效性 */
        // 获取账户名
        String accountName;
        try {
            accountName = JwtUtils.parseToken(JwtUtils.AVAILABLE_JWT_SECRET_KEY, token).getBody().getId();
        } catch (Exception e) {
            throw new BlogAccountNotRightExceptionTips(CommonGlobalRef.ACCOUNT_AT_NOT_RIGHT_TIPS);
        }
        // 从缓存中获取配置
        CoreSysAdmin sysAdmin = ((AdminQueryServiceImpl) adminQueryService).getSetting(accountName);
        // 封装成UserDetails，并显式调用UsernamePasswordAuthenticationToken，设置当前请求为已验证的身份状态
        UsernamePasswordAuthenticationToken authenticated =
            new UsernamePasswordAuthenticationToken(new LoginUserInfo(sysAdmin), null, null);
        // 将当前UserDetails用户信息，即已经验证通过的数据存入SecurityContext中，便于后续过滤器获取
        SecurityContextHolder.getContext().setAuthentication(authenticated);
    }
}
