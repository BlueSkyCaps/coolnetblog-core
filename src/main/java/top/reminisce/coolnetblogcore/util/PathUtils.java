package top.reminisce.coolnetblogcore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    private static final String[] LOCAL_ADDR_ = new String[]{"0:0:0:0:0:0:0:1", "127.0.0.1", "localhost"};

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
                ip = ip.split(DOT_)[0];
                return ip;
            }
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (! ObjectUtils.isEmpty(ip)){
            return ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (! ObjectUtils.isEmpty(ip)){
            return ip;
        }
        ip = request.getRemoteAddr();
        if (ObjectUtils.isEmpty(ip)){
            // log.info("处理客户端发出的请求ip：当前上下文请求获取的ip为null");
            throw new BlogException("处理客户端发出的请求ip：当前上下文请求获取的ip为null");
        }
        if (ip.equals(LOCAL_ADDR_[0]) || ip.equals(LOCAL_ADDR_[1]))
            ip = LOCAL_ADDR_[2];
        return ip;
    }
    public static String getRealPath(HttpServletRequest request, String ref) {
        ServletContext servletContext = request.getServletContext();
        return servletContext.getRealPath(ref);
    }

    /**
     * 获取当前项目Resource根目录路径
     * @return 当前Resource根目录路径
     */
    public static String getCurrentProjectStaticResourcesPath(){
        try {
            return new File("src/main/resources").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取当前项目Source根目录路径
     * @return 当前Source根目录路径
     */
    public static String getCurrentProjectSourcePath(){
        try {
            return new File("src/main/java").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回文件名后缀，若无后缀则null
     * @return
     */
    public static String getFileNameSuffix(String originalFilename) {
        boolean b = StringUtils.hasText(originalFilename);
        if (! b){
            throw new RuntimeException("文件名必须不为空");
        }
        int i = originalFilename.lastIndexOf(".");
        if (i != -1){
            return originalFilename.substring(i+1);
        }
        return null;
    }

}
