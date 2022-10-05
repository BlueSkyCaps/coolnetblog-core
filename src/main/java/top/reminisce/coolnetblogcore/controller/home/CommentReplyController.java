package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.home.HomeCommentReplyService;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 前台界面处理评论留言接口
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
    @GetMapping({"comment/article/{articleId}"})
    public Result getDetail(@PathVariable Integer articleId, @NotNull Integer pageIndex,
                            @Value("10") Integer commentCount, @Value("10") Integer replyCount){
        List<CoreComment> commentsCarryRepliesByArticleIdBasedSlide = homeCommentReplyService
            .getCommentsCarryRepliesByArticleIdBasedSlide(articleId, pageIndex, commentCount, replyCount);
        return ResultPack.fluent(commentsCarryRepliesByArticleIdBasedSlide);
    }
}
