package top.reminisce.coolnetblogcore.repository.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.common.BlogException;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreThumbUp;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ThumpUp数据访问层 处理点赞 -> mongo based
 * @author BlueSky
 * @date 2022/10/2
 */

public interface ThumpUpRepository extends MongoRepository<CoreThumbUp, String> {
    /**
     * 查询此点赞是否已经存在：根据客户端ip、点赞的内容id、点赞的内容类型
     * @param template MongoTemplate
     * @param sourceId 点赞的内容id
     * @param sourceType 点赞的内容类型
     * @param ip 客户端ip
     * @return true，存在匹配；false，无
     */
    default boolean thumbUpAlreadyExists(MongoTemplate template, Integer sourceId, Integer sourceType, String ip) {
        if (Objects.isNull(template)) {
            throw new BlogException("MongoTemplate必须不为null！");
        }
        CriteriaDefinition criteriaDefinition = new Criteria()
            .and("sourceId").is(sourceId)
            .and("sourceType").is(sourceType)
            .and("ip").is(ip);
        Query query = new Query(criteriaDefinition);
        return template.exists(query, CoreThumbUp.class);
    }
}
