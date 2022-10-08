package top.reminisce.coolnetblogcore.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ElasticSearch, Rest High Level Client配置类。使基于springboot集成，而不是直接使用独立的es官方RHLC
 * @author BlueSky
 * @date 2022/10/3
 */
@Configuration
public class ElasticSearchHighClientConfig extends AbstractElasticsearchConfiguration {
    @Autowired
    private ElasticSearchConfigProperties elasticSearchConfigProperties;

    @NotNull
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo(elasticSearchConfigProperties.getHostAndPort())
            .withBasicAuth(elasticSearchConfigProperties.getUsername(), elasticSearchConfigProperties.getPassword())
            .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
