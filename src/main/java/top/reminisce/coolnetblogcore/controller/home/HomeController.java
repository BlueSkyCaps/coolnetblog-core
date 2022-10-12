package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;

import java.util.List;

/**
 * 前台界面所需数据的接口访问层
 * @author BlueSky
 * @date 2022/10/1
 */
@RestController
public class HomeController {

    private final HomeGlobalNeedService homeService;

    public HomeController(HomeGlobalNeedService homeService) {
        this.homeService = homeService;
    }

    /**
     * 在客户端首次加载首页时，返回首次加载所需的全局数据体，不包含文章。
     * @return Result数据体
     */
    @GetMapping({"global", "init"})
    public Result getGlobalEachNeedData(){
        Object gdb = this.homeService.dealGlobalEachNeedData();
        return ResultPack.fluent(gdb);
    }

    /**
     * 按来源获取文章
     * @return Result数据体
     */
    @GetMapping({"article"})
    public Result getArticles(String from, String keyword, Integer menuId, Integer pageIndex){
        List<ArticleSearch> searchArticles = ((HomeServiceImpl) homeService).searchArticles(from, keyword, menuId, pageIndex);
        return ResultPack.fluent(searchArticles);
    }
}
