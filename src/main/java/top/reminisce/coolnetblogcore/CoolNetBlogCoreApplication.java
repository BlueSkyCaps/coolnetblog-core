package top.reminisce.coolnetblogcore;

import org.apache.ibatis.reflection.SystemMetaObject;
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
        // System.out.println("----------------------");
        // System.out.println(System.getProperty("java.version"));
        // System.out.println("----------------------");
        SpringApplication.run(CoolNetBlogCoreApplication.class, args);
    }

}
