package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;

/**
 * 前台界面处理评论留言接口
 * @author BlueSky
 * @date 2022/10/1
 */
@RestController
public class CommentController {
    @Autowired
    private HomeGlobalNeedService homeService;
    /**
     * 获取某文章
     * @return Result数据体 文章详细数据
     */
    @GetMapping({"detail/{custUri}"})
    public Result getDetail(@PathVariable String custUri, Integer id){
        CoreArticle article = ((HomeServiceImpl) homeService).getDetailBasedQueryContext(custUri, id);
        return ResultPack.fluent(article);
    }
}
