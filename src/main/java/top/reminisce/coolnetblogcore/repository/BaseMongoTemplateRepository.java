package top.reminisce.coolnetblogcore.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;

import java.util.Objects;

/**
 * @author BlueSky
 * @date 2022/10/8
 */
public interface BaseMongoTemplateRepository {

    static void initCheck(MongoTemplate template, Class<?> entityClass){
        if (Objects.isNull(template)) {
            throw new BlogException("MongoTemplate必须不为null！");
        }
        if (ObjectUtils.isEmpty(entityClass)){
            throw new BlogException("实体对象类必须不为null！");
        }
    }
    /**
     * 根据条件表达式对象的定义，查询相应记录是否存在：
     * @param template 通常是由spring管控的数据访问层实现的Template对象，如MongoTemplate、ElasticsearchRestTemplate等
     * @param criteriaDefinition 条件表达式对象的定义
     * @param entityClass 实体对象类
     * @return true，存在匹配；false，无
     */
    default boolean conditionWhereAlreadyExists(MongoTemplate template, CriteriaDefinition criteriaDefinition,
                                                Class<?> entityClass) {
        initCheck(template, entityClass);

        Query query = new Query(criteriaDefinition);
        return template.exists(query, entityClass);
    }

    /**
     * 根据条件表达式对象的定义,查询匹配计数
     * @param template 通常是由spring管控的数据访问层实现的Template对象，如MongoTemplate、ElasticsearchRestTemplate等
     * @param criteriaDefinition 条件表达式对象的定义
     * @return 匹配数
     */
    default Integer conditionWhereCount(MongoTemplate template, CriteriaDefinition criteriaDefinition,
                                                Class<?> entityClass) {
        initCheck(template, entityClass);
        Query query = new Query(criteriaDefinition);
        return Math.toIntExact(template.count(query, entityClass));
    }
}
