package top.reminisce.coolnetblogcore.util.bean;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/** 获取Bean，由spring集成管控的Bean。
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public class SpringBeanUtils {

    private final ApplicationContext context;

    public SpringBeanUtils(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 获取MongoTemplate bean
     */
    public MongoTemplate getMongoTemplate(){
        return context.getBean(MongoTemplate.class);
    }

    /**
     * 获取RestHighLevelClient bean
     */
    public RestHighLevelClient getElasticsearchRestHighClient(){
        return context.getBean(RestHighLevelClient.class);
    }

    /**
     * 获取ElasticsearchRestTemplate bean
     */
    public ElasticsearchRestTemplate getElasticsearchRestTemplate(){
        return context.getBean(ElasticsearchRestTemplate.class);
    }
}
