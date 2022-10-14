package top.reminisce.coolnetblogcore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author BlueSky
 * @date 2022/10/14
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 设置允许跨域 任何
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowedOriginPatterns("*");
    }
}
