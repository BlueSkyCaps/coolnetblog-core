package top.reminisce.coolnetblogcore.util.bean;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public class SpringBeanUtils {

    private final ApplicationContext context;

    public SpringBeanUtils(ApplicationContext context) {
        this.context = context;
    }

    public MongoTemplate getMongoTemplate(){
        return context.getBean(MongoTemplate.class);
    }
    public RestHighLevelClient getElasticsearchRestHighClient(){
        return context.getBean(RestHighLevelClient.class);
    }
}
