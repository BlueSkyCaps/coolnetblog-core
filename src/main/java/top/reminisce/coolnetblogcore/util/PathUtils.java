package top.reminisce.coolnetblogcore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.exception.BlogException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/6
 */
@Slf4j
public class PathUtils {
    public static List<String> UN_VALID_IP_ARRAYS = new ArrayList<String>(){
        {
            add("unknown");
            add("UNKNOWN");
        }
    };
    private static final String DOT_ = ",";

    /**
     * 根据请求上下文获取客户端源ip地址
     * @param request HttpServletRequest上下文
     * @return 客户端源ip地址
     */
    public static String getClientSourceIp(HttpServletRequest request){
        if (request == null){
            throw new BlogException("处理客户端发出的请求ip：HttpServletRequest不得为null！");
        }
        String ip = request.getHeader("X-Real-IP");
        if (! ObjectUtils.isEmpty(ip) && ! UN_VALID_IP_ARRAYS.contains(ip)){
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if (! ObjectUtils.isEmpty(ip)){
            if (ip.contains(DOT_)){
                ip = ip.split(",")[0];
            }
        }
        if (ObjectUtils.isEmpty(ip)){
            log.info("处理客户端发出的请求ip：当前上下文请求获取的ip为null");
            throw new BlogException("处理客户端发出的请求ip：当前上下文请求获取的ip为null");
        }
        return ip;
    }
}
