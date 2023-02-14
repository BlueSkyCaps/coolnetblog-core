package top.reminisce.coolnetblogcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author BlueSkyCarry
 */
@SpringBootApplication
@EnableCaching
public class CoolNetBlogCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoolNetBlogCoreApplication.class, args);
    }

}
