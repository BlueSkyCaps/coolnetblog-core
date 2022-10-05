package top.reminisce.coolnetblogcore.service.home.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import top.reminisce.coolnetblogcore.common.BlogException;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.mongo.CommentRepository;
import top.reminisce.coolnetblogcore.repository.mongo.ReplyRepository;
import top.reminisce.coolnetblogcore.service.home.HomeCommentReplyService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Service
public class HomeCommentReplyServiceImpl extends AbstractHomeArticleQueryService implements HomeCommentReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    public HomeCommentReplyServiceImpl(CommentRepository commentRepository, ReplyRepository replyRepository) {
        this.commentRepository = commentRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public List<CoreComment> getCommentsCarryRepliesByArticleIdBasedSlide(Integer articleId, Integer index, Integer commentCount, Integer replyCount) {
        CoreArticle article = super.articleMapper.selectById(articleId);
        if (ObjectUtils.isEmpty(article)){
            throw new BlogException("文章已不存在，请刷新。");
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "commentTime");
        Pageable pageable = PageRequest.of(index - 1, commentCount, sort);
        // 获取文章的评论，指定数量，分页。
        List<CoreComment> comments = commentRepository.findBySourceIdAndSourceType(articleId, 1, pageable);
        // 迭代获取每个评论的回复数据，初始化第一页回复
        for (CoreComment comment : comments) {
            comment.setReplies(getRepliesByCommentIdBasedSlide(comment.getId(), 1, replyCount));
        }
        return comments;
    }

    @Override
    public List<CoreReply> getAllRepliesByCommentId(Integer commentId) {
        throw new NotImplementedException();
    }

    @Override
    public List<CoreReply> getRepliesByCommentIdBasedSlide(Integer commentId, Integer index, Integer count) {
        Sort sort = Sort.by(Sort.Direction.DESC, "replyTime");
        Pageable pageable = PageRequest.of(index - 1, 10, sort);
        return replyRepository.findByCommentId(commentId, pageable);
    }
}
