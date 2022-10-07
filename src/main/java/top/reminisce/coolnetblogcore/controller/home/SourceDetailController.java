package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.dto.ReplyAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.ThumbUpDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreThumbUp;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;
import top.reminisce.coolnetblogcore.service.home.ThumbUpService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 前台界面获取具体内容的接口
 * @author BlueSky
 * @date 2022/10/1
 */
@RestController
public class SourceDetailController {
    @Autowired
    private HomeGlobalNeedService homeService;
    @Autowired
    private ThumbUpService thumbUpService;
    /**
     * 获取某文章
     * @return Result数据体 文章详细数据<br>
     * detail上下文路径匹配：兼容原有链接
     */
    @GetMapping({"article/{custUri}", "detail/{custUri}"})
    public Result getArticleDetail(@PathVariable String custUri, Integer id){
        CoreArticle article = ((HomeServiceImpl) homeService).getDetailBasedQueryContext(custUri, id);
        return ResultPack.fluent(article);
    }

    /**
     * 为内容点赞
     * @return 数据体 包装点赞后的数据
     */
    @PostMapping({"thumb-up"})
    public Result thumbUpSource(@RequestBody @Valid ThumbUpDto thumbUpDto, @NotNull HttpServletRequest request){
        CoreThumbUp thumbUp = thumbUpService.thumbUpSource(thumbUpDto, request);
        return ResultPack.fluent(thumbUp);
    }
}
