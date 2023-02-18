package top.reminisce.coolnetblogcore.service.admin.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.repository.mongo.CommentRepository;
import top.reminisce.coolnetblogcore.repository.mongo.ReplyRepository;
import top.reminisce.coolnetblogcore.service.admin.AdminCommentReplySaveService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeCommentReplyServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台评论实现类 用于删除评论、管理员添加评论
 * @author BlueSky
 * @date 2022/10/25
 */
@Service
public class AdminCommentReplySaveServiceImpl extends HomeCommentReplyServiceImpl implements AdminCommentReplySaveService {
    public AdminCommentReplySaveServiceImpl(CommentRepository commentRepository, ReplyRepository replyRepository) {
        super(commentRepository, replyRepository);
    }

    @Override
    public CoreComment addCommentByAdmin(CoreComment comment) {
        return null;
    }

    @Override
    public void removeComment(Integer id) {
        super.commentRepository.deleteById(id);
        // 根据评论删关联回复
        List<Integer> needToDelRids = super.replyRepository
            .finRidBasedRelatedCommentIdsIn(super.beanUtils.getMongoTemplate(), Collections.singletonList(id));
        super.replyRepository.deleteAllById(needToDelRids);
    }

    @Override
    public CoreReply addReplyByAdmin(CoreReply reply) {
        return null;
    }

    @Override
    public void removeReply(Integer id) {
        super.replyRepository.deleteById(id);
    }

    @Override
    public void removeSourceRelated(Integer sourceId, Integer sourceType) {
        // 先获取需要删除的评论，以列表形式返回
        Query query = new Query(new Criteria()
            .and("sourceId").is(sourceId)
            .and("sourceType").is(sourceType));
        query.fields().include("id");
        List<?> data = super.commentRepository
            .conditionWhereFindData(super.beanUtils.getMongoTemplate(), query, CoreComment.class);
        List<Integer> needToDelCids = data.stream().map(c->((CoreComment)c).getId()).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(needToDelCids)){
            return;
        }
        // 删关联评论
        super.commentRepository.deleteAllById(needToDelCids);
        // 根据评论继续删关联回复
        List<Integer> needToDelRids = super.replyRepository.finRidBasedRelatedCommentIdsIn(super.beanUtils.getMongoTemplate(), needToDelCids);
        super.replyRepository.deleteAllById(needToDelRids);
    }
}
