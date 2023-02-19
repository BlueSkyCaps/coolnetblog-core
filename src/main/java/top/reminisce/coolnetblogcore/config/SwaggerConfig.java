package top.reminisce.coolnetblogcore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author BlueSky
 * @date 2023/2/18
 */
@Configuration
@EnableSwagger2
// can not missing the Scan
@ComponentScan("top.reminisce.coolnetblogcore.controller")
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .build();
    }
}
