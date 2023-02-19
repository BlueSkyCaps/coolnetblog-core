package top.reminisce.coolnetblogcore.service.home.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.handler.exception.BlogLeaveLimitExceptionTips;
import top.reminisce.coolnetblogcore.handler.exception.BlogNotExistExceptionTips;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.ReplyAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.mongo.CommentRepository;
import top.reminisce.coolnetblogcore.repository.mongo.ReplyRepository;
import top.reminisce.coolnetblogcore.service.home.HomeCommentReplyService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;
import top.reminisce.coolnetblogcore.util.PathUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;
import top.reminisce.coolnetblogcore.util.mapperConvert.CommentAddDtoToCommentMapperUtils;
import top.reminisce.coolnetblogcore.util.mapperConvert.ReplyAddDtoToReplyMapperUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 前台评论回复实现类 用于获取评论、用户留言，但不包括删除的实现。删除操作请见实现类
 * {@link top.reminisce.coolnetblogcore.service.admin.impl.AdminCommentReplySaveServiceImpl AdminCommentReplySaveServiceImpl}。
 * @see top.reminisce.coolnetblogcore.service.admin.AdminCommentReplySaveService
 * @author BlueSky
 * @date 2022/10/1
 */
@Service
public class HomeCommentReplyServiceImpl extends AbstractHomeArticleQueryService implements HomeCommentReplyService {

    protected final CommentRepository commentRepository;
    protected final ReplyRepository replyRepository;

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
        source = initSourceExistLogic(comment.getSourceId(), comment.getSourceType());
        if (comment.getSourceType()==1){
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
            throw new BlogNotExistExceptionTips("评论已不存在，请刷新。");
        }
        // 再检查关联的评论的文章
        source = initSourceExistLogic(relatedComment.getSourceId(), relatedComment.getSourceType());
        if (relatedComment.getSourceType() == 1){
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
        comment.setCommentTime(TimeUtils.currentDateTime());
        comment.setId(null);
        comment.setAdmin(false);
    }

    private void packReplyEntity(CoreReply reply){
        reply.setReplyTime(TimeUtils.currentDateTime());
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
                throw new BlogNotExistExceptionTips("文章已不存在，请刷新。");
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
        leaveLimitCountCheck(request);
        CoreComment comment = CommentAddDtoToCommentMapperUtils.INSTANCE.commentAddDtoToComment(commentAddDto);
        initSourceAddCommentLogic(comment);
        return this.commentRepository.save(comment);
    }

    @Override
    public CoreReply addReplyPackProcessor(ReplyAddDto replyAddDto, HttpServletRequest request) {
        leaveLimitCountCheck(request);
        CoreReply reply = ReplyAddDtoToReplyMapperUtils.INSTANCE.replyAddDtoToReply(replyAddDto);
        initSourceAddReplyLogic(reply);
        return this.replyRepository.save(reply);
    }

    /**
     * 检查当前客户端ip当日的评论回复数是否超过设置的每日限制数
     */
    private void leaveLimitCountCheck(HttpServletRequest request){
        CoreSysAdmin coreSysAdmin = super.getSysAdminExcludeSecurity();
        Integer leaveLimitCount = coreSysAdmin.getSiteSetting().getLeaveLimitCount();
        if (leaveLimitCount==null || leaveLimitCount<=0){
            return;
        }
        String ip = PathUtils.getClientSourceIp(request);
        Date nowDate;
        nowDate = TimeUtils.dateExcludeTime(TimeUtils.currentDateTime());
        Date nextDate = TimeUtils.dateTimeOffsetDay(nowDate, 1);
        CriteriaDefinition criteria = new Criteria()
            .and("clientIp").is(ip)
            .and("commentTime").gt(nowDate)
            .and("commentTime").lt(nextDate);
        Integer cCount = this.commentRepository.conditionWhereCount(super.beanUtils.getMongoTemplate(), criteria, CoreComment.class);
        criteria = new Criteria()
            .and("clientIp").is(ip)
            .and("replyTime").gt(nowDate)
            .and("replyTime").lt(nextDate);
        Integer rCount = this.replyRepository.conditionWhereCount(super.beanUtils.getMongoTemplate(), criteria, CoreReply.class);
        if (cCount+rCount>leaveLimitCount) {
            throw new BlogLeaveLimitExceptionTips("当日留言次数已用完，请第二日再来哦，谢谢！");
        }
    }
}
