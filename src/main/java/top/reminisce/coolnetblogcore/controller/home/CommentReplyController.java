package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.ReplyAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.home.HomeCommentReplyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 前台界面处理评论接口
 * @author BlueSky
 * @date 2022/10/1
 */
@RestController
public class CommentReplyController {
    @Qualifier("homeCommentReplyServiceImpl")
    @Autowired
    private HomeCommentReplyService homeCommentReplyService;


    /**
     * 根据内容id获取评论，分页
     * @param sourceId 源内容id，如文章id
     * @param pageIndex 分页页码
     * @param commentCount 每页获取的评论数
     * @param replyCount 每条评论携带的回复数
     * @return Result数据体 评论以及携带的回复的列表
     */
    @GetMapping({"source/{sourceType}/{sourceId}/comment"})
    public Result getComments(@PathVariable Integer sourceId, @PathVariable Integer sourceType, @NotNull Integer pageIndex,
                              @DefaultValue("10") Integer commentCount, @DefaultValue("10") Integer replyCount){
        List<CoreComment> commentsCarryRepliesByArticleIdBasedSlide = homeCommentReplyService
            .getCommentsCarryRepliesBySourceIdBasedSlide(sourceId, sourceType, pageIndex, commentCount, replyCount);
        return ResultPack.fluent(commentsCarryRepliesByArticleIdBasedSlide);
    }

    /**
     * 为内容新增评论
     * @param commentAdd 前端评论数据
     * @param request 请求体对象
     * @return 数据体 新增后的当前评论数据
     */
    @PostMapping({"comment"})
    public Result addComment( @RequestBody @Valid CommentAddDto commentAdd, @NotNull HttpServletRequest request){
        CoreComment comment = homeCommentReplyService.addCommentPackProcessor(commentAdd, request);
        return ResultPack.fluent(comment);
    }

    /**
     * 为评论新增回复
     * @param replyAdd 前端回复数据
     * @param request 请求体对象
     * @return 数据体 新增后的当前回复数据
     */
    @PostMapping({"reply"})
    public Result addReply(@RequestBody @Valid ReplyAddDto replyAdd, @NotNull HttpServletRequest request){
        CoreReply reply = homeCommentReplyService.addReplyPackProcessor(replyAdd, request);
        return ResultPack.fluent(reply);
    }
}
