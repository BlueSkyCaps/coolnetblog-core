package top.reminisce.coolnetblogcore.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import top.reminisce.coolnetblogcore.pojo.ao.ElasticSearchConfigProperties;

import java.time.Duration;

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
            // .withConnectTimeout(elasticSearchConfigProperties.getConnectTimeout())
            // .withSocketTimeout(elasticSearchConfigProperties.getSocketTimeout())
            // .withClientConfigurer(
            //     // 配置KeepAlive，避免空闲后的第一次连接出现DataAccessResourceFailureException异常
            //     RestClients.RestClientConfigurationCallback.from(
            //         httpAsyncClientBuilder -> httpAsyncClientBuilder.setKeepAliveStrategy(
            //             (response, context)-> Duration.ofMinutes(1).toMillis()
            //         )
            //     )
            // )
            .build();
        return RestClients.create(clientConfiguration).rest();
    }

}
