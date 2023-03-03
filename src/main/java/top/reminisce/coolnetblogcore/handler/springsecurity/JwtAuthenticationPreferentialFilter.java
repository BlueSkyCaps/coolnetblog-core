package top.reminisce.coolnetblogcore.handler.springsecurity;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.reminisce.coolnetblogcore.config.springsecurity.LoginUserInfo;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.service.admin.AdminQueryService;
import top.reminisce.coolnetblogcore.service.admin.impl.AdminQueryServiceImpl;
import top.reminisce.coolnetblogcore.util.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.*;

/**
 * 当请求后台接口时，需要验证token的过滤拦截器。此过滤器在当前请求线程中应该率先被执行。
 * @author BlueSky
 * @date 2022/10/12
 */
@Component
public class JwtAuthenticationPreferentialFilter extends OncePerRequestFilter {
    @Qualifier("adminQueryServiceImpl")
    @Autowired
    private AdminQueryService adminQueryService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        // 请求头中不包含token 放行。若是请求允许的接口，后续security过滤器处理响应；若是后台接口，后续处理器拦截抛出
        if (! StringUtils.hasText(token)) {
            super.doFilter(request, response, filterChain);
            return;
        }
        /* 解析jwt 验证有效性 */
        // 获取账户名
        String accountName;
        try {
            accountName = JwtUtils.parseToken(JwtUtils.AVAILABLE_JWT_SECRET_KEY, token).getBody().getId();
        } catch (RuntimeException e) {
            extracted(request, ACCOUNT_TOKEN_INVALID_TIPS, response);
            return;
        }
        // 从缓存中查找当前账户是否已经注销过，注销需要再次登陆验证
        Boolean logout = this.stringRedisTemplate.opsForSet().isMember(REDIS_LOGOUT_KEY_NAME, accountName);
        if (Boolean.TRUE.equals(logout)){
            extracted(request, ACCOUNT_LOGOUT_NOT_AT_TIPS, response);
            return;
        }
        // 根据账户获取配置
        CoreSysAdmin sysAdmin = ((AdminQueryServiceImpl) adminQueryService).getSysAdmin(accountName);
        // 封装成UserDetails，并显式调用UsernamePasswordAuthenticationToken，设置当前请求为已验证的身份状态
        UsernamePasswordAuthenticationToken authenticated =
            new UsernamePasswordAuthenticationToken(new LoginUserInfo(sysAdmin), null, null);
        /*
        * 将当前UserDetails用户信息，即已经验证通过的数据存入SecurityContext中，
        * 便于后续过滤器或请求方法获取（如登出操作时、执行具体接口方法需要获取当前上下文用户数据时..）
        *  */
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        super.doFilter(request, response, filterChain);
    }

    /**
     *
     * 封装异常到当前请求，转发传递给控制器层捕获抛出，避免全局捕获异常@ControllerAdvice无法在filter层有用
     * @param request
     * @param accountTokenInvalidTips
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private static void extracted(@NotNull HttpServletRequest request, String accountTokenInvalidTips, @NotNull HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(EXCEPTION_IN_FILTER_FORWARD_ATTRIBUTE,
            new BlogAccountNotRightExceptionTips(accountTokenInvalidTips));
        // 转发传递给指定控制器
        request.getRequestDispatcher("/forward/exception-in-filter").forward(request, response);
    }
}
