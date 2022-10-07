package top.reminisce.coolnetblogcore.service.home.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import top.reminisce.coolnetblogcore.common.BlogException;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.ReplyAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.mongo.CommentRepository;
import top.reminisce.coolnetblogcore.repository.mongo.ReplyRepository;
import top.reminisce.coolnetblogcore.service.home.HomeCommentReplyService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;
import top.reminisce.coolnetblogcore.util.TimeUtils;
import top.reminisce.coolnetblogcore.util.mapperConvert.CommentAddDtoToCommentMapperUtils;
import top.reminisce.coolnetblogcore.util.mapperConvert.ReplyAddDtoToReplyMapperUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

    /**
     * 初始化待插入的评论实体逻辑
     * @param comment 评论实体<br>
     * comment.sourceId 评论的来源内容id，比如文章id<br>
     * comment.sourceType 评论的来源内容类型，比如文章
     * @return 评论的来源内容数据，如文章实体
     */
    private Object initSourceAddCommentLogic(CoreComment comment){
        Object source = null;
        comment.setPassed(true);
        if (comment.getSourceType()==1){
            // 评论的来源内容类型为文章，处理相应的评论封装
            source = super.getArticleById(comment.getSourceId());
            if (ObjectUtils.isEmpty(source)){
                throw new BlogException("文章已不存在，请刷新。");
            }
            // 转化为文章对象
            CoreArticle article =  ((CoreArticle)source);
            // 评论类型来源是文章，且文章的评论设置为需要审核通过评论才能显示
            if (article.getCommentType()==2){
                comment.setPassed(false);
            }
        }
        packCommentEntity(comment);
        return source;
    }

    /**
     * 初始化待插入的回复实体逻辑
     * @param reply 回复实体<br>
     * comment.commentId 回复的评论id<br>
     * @return 回复的最上方来源内容数据，如文章实体
     */
    private Object initSourceAddReplyLogic(CoreReply reply){
        Object source = null;
        reply.setPassed(true);
        Integer relatedCid = reply.getCommentId();
        // 检查关联的评论
        CoreComment relatedComment = commentRepository.findById(relatedCid).orElse(null);
        if (relatedComment == null) {
            throw new BlogException("评论已不存在，请刷新。");
        }
        // 再检查关联的评论的文章
        if (relatedComment.getSourceType() == 1){
            // 评论的来源内容类型为文章，处理相应的评论封装
            source = super.getArticleById(relatedComment.getSourceId());
            if (ObjectUtils.isEmpty(source)){
                throw new BlogException("文章已不存在，请刷新。");
            }
            // 转化为文章对象
            CoreArticle article =  ((CoreArticle)source);
            // 回复的关联文章的评论设置为需要审核通过评论才能显示
            if (article.getCommentType()==2){
                reply.setPassed(false);
            }
        }
        packReplyEntity(reply);
        return source;
    }

    private void packCommentEntity(CoreComment comment){
        try {
            comment.setCommentTime(TimeUtils.currentDateTime());
        } catch (ParseException e) {
            throw new RuntimeException("添加评论：设置当前评论日期失败。"+e.getMessage());
        }
        comment.setId(null);
        comment.setAdmin(false);
    }

    private void packReplyEntity(CoreReply reply){
        try {
            reply.setReplyTime(TimeUtils.currentDateTime());
        } catch (ParseException e) {
            throw new RuntimeException("添加回复：设置当前回复日期失败。"+e.getMessage());
        }
        reply.setId(null);
        reply.setAdmin(false);
    }

    /**
     * 检查评论所对应的源内容是否存在
     * @param sourceId 评论的来源内容id，比如文章id
     * @param sourceType 评论的来源内容类型，比如文章
     * @return 评论的来源内容数据，如文章实体
     */
    private Object initSourceExistLogic(Integer sourceId, Integer sourceType){
        if(ObjectUtils.isEmpty(sourceId) || ObjectUtils.isEmpty(sourceType)){
            throw new BlogException("原内容id、原内容类型传递的值不得为空");
        }
        Object source = null;
        if (sourceType==1){
            // 评论的来源内容类型为文章，处理相应的评论封装
            source = super.getArticleById(sourceId);
            if (ObjectUtils.isEmpty(source)){
                throw new BlogException("文章已不存在，请刷新。");
            }
        }
        return source;
    }

    @Override
    public List<CoreComment> getCommentsCarryRepliesBySourceIdBasedSlide(Integer sourceId, Integer sourceType, Integer index, Integer commentCount, Integer replyCount) {
        initSourceExistLogic(sourceId, sourceType);
        Sort sort = Sort.by(Sort.Direction.DESC, "commentTime");
        Pageable pageable = PageRequest.of(index - 1, commentCount, sort);
        // 获取文章的评论，指定数量，分页。
        List<CoreComment> comments = commentRepository.findBySourceIdAndSourceType(sourceId, 1, pageable);
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

    @Override
    public CoreComment addCommentPackProcessor(CommentAddDto commentAddDto, HttpServletRequest request) {
        CoreComment comment = CommentAddDtoToCommentMapperUtils.INSTANCE.commentAddDtoToComment(commentAddDto);
        initSourceAddCommentLogic(comment);
        return this.commentRepository.save(comment);
    }

    @Override
    public CoreReply addCommentReplyPackProcessor(ReplyAddDto replyAddDto, HttpServletRequest request) {
        CoreReply reply = ReplyAddDtoToReplyMapperUtils.INSTANCE.replyAddDtoToReply(replyAddDto);
        initSourceAddReplyLogic(reply);
        return this.replyRepository.save(reply);
    }
}
