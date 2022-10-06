package top.reminisce.coolnetblogcore.service.home;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/5
 */
public interface HomeCommentReplyService {
    /**
     * 根据源内容id获取评论和关联回复，使用分页
     * @param sourceId 内容id
     * @param sourceType 内容类型，通常文章是1
     * @param index 页码
     * @param commentCount 每页获取的评论数
     * @param replyCount 每条评论获取的关联回复数
     */
    List<CoreComment> getCommentsCarryRepliesByArticleIdBasedSlide(Integer sourceId, Integer sourceType, Integer index,
                                   Integer commentCount, Integer replyCount);

    /**
     * 根据评论id获取所有关联回复
     */
    List<CoreReply> getAllRepliesByCommentId(@NotNull Integer commentId);


    /**
     * 根据评论id获取关联回复，使用分页
     * @param commentId 评论id
     * @param index 回复页码
     * @param count 每页回复数
     */
    List<CoreReply> getRepliesByCommentIdBasedSlide(@NotNull Integer commentId, @NotNull Integer index, @Value("10") Integer count);

    /**
     * 新增评论处理
     * @return 新增后的当前评论数据
     */
    CoreComment addCommentPackProcessor(CommentAddDto commentAdd, HttpServletRequest request);

    /**
     * 新增评论回复
     * @return
     */
    CoreReply addCommentReplyPackProcessor();

}
