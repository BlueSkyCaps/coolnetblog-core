package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.home.HomeCommentReplyService;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 前台界面处理评论接口
 * @author BlueSky
 * @date 2022/10/1
 */
@RestController
public class CommentReplyController {
    @Autowired
    private HomeCommentReplyService homeCommentReplyService;
    /**
     * 根据文章id获取评论，分页
     * @param articleId 文章id
     * @param pageIndex 分页页码
     * @param commentCount 每页获取的文章数
     * @param replyCount 每条评论携带的回复数
     * @return Result数据体 评论以及携带的回复的列表
     */
    @GetMapping({"article/{articleId}/comment"})
    public Result getDetail(@PathVariable Integer articleId, @NotNull Integer pageIndex,
                            @Value("10") Integer commentCount, @Value("10") Integer replyCount){
        List<CoreComment> commentsCarryRepliesByArticleIdBasedSlide = homeCommentReplyService
            .getCommentsCarryRepliesByArticleIdBasedSlide(articleId, 1, pageIndex, commentCount, replyCount);
        return ResultPack.fluent(commentsCarryRepliesByArticleIdBasedSlide);
    }

    /**
     * 为内容新增评论
     * @param commentAdd 前端评论数据
     * @param request 请求体对象
     * @return 数据体
     */
    @PostMapping({"comment"})
    public Result addComment( @RequestBody @Valid CommentAddDto commentAdd, @NotNull HttpServletRequest request){
        CoreComment comment = homeCommentReplyService.addCommentPackProcessor(commentAdd, request);
        return ResultPack.fluent(comment);
    }


}
