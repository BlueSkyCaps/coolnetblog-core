package top.reminisce.coolnetblogcore.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author BlueSky
 * @date 2023/2/19
 */
@Configuration
public class MongoConfig {
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;


    /**
     * 为MongoTemplate设置自定转换对象。<br/>
     * add DateTextStringToDateCustomConverter：解决日期字符串到java Date对象转换失败的问题
     * @return MongoCustomConversions自定义转换对象
     * @note 此MongoCustomConversions对象必须为bean，否则mongoTemplate.setCustomConversions方法添加抛出convert等异常
     */
    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(DateTextStringToDateCustomConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }

    /**
     * 获取配置后的MongoTemplate
     * @return
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory);
        // MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.setCustomConversions(customConversions());
        // 最终调用，否则无法成功添加自定义转换器
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;

    }

    /**
     * 将日期字符串转换为日期对象的转换器
     */
    private enum DateTextStringToDateCustomConverter implements Converter<String, Date> {
        INSTANCE;
        @Override
        public Date convert(@NotNull String source) {
            Date date;
            try {
                date = TimeUtils.dateTextConvertToDate(source);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return date;
        }
    }
}
