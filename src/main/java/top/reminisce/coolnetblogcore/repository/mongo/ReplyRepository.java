package top.reminisce.coolnetblogcore.repository.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.repository.BaseMongoTemplateRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reply数据访问层 处理评论关联的回复 -> mongo based
 * @author BlueSky
 * @date 2022/10/2
 */

public interface ReplyRepository extends MongoRepository<CoreReply, Integer>, BaseMongoTemplateRepository {
    /**
     * 获取指定评论id获取关联回复，使用分页
     * @param commentId 评论id
     * @param pageable 分页对象
     * @return 分页获取到的回复列表
     */
    List<CoreReply> findByCommentId(Integer commentId, Pageable pageable);
    /**
     * 根据提供的评论id范围，获取相关的所有回复id<br>
     * 基于mongodb中的查找文档 $in 运算符<br>
     * 通常用于删除评论关联的所有回复
     *
     * @param template MongoTemplate
     * @param cids     要查找范围的评论id列表
     * @return 属于评论范围的回复id，列表
     */
    default List<Integer> finRidBasedRelatedCommentIdsIn(MongoTemplate template, List<Integer> cids) {
        if (template==null){
            throw new BlogException("MongoTemplate对象不得为空！");
        }
        if (ObjectUtils.isEmpty(cids)){
            throw new BlogException("传递的评论id列表为空！");
        }
        // 构建$in操作运算符
        Criteria criteria = new Criteria("commentId").in(cids);
        Query query = new Query(criteria);
        query.fields().include("id");
        return template.find(query, CoreReply.class).stream().map(CoreReply::getId).collect(Collectors.toList());
    }
}
