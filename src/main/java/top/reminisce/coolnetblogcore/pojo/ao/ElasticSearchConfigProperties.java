package top.reminisce.coolnetblogcore.pojo.ao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author BlueSky
 * @date 2022/10/3
 */
@Configuration
@ConfigurationProperties(prefix = "rest-high-level-elasticsearch")
@Getter
@Setter
public class ElasticSearchConfigProperties {
    private String hostAndPort;
    private String username;
    private String password;
    private int connectTimeout;
    private int socketTimeout;
}
