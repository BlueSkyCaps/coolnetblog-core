package top.reminisce.coolnetblogcore.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于配置Mybatis和Mybatis-Plus的配置
 * @author BlueSky
 * @date 2023/2/14
 */
@Configuration
public class MybatisConfig {
    /**
     * 设置分页插件，mp能有效分页
     */
    @Bean
    public MybatisPlusInterceptor mpPageEnable(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // PaginationInnerInterceptor 数据库方言默认从mybatis中得知
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
