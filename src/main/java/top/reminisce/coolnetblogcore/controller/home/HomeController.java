package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
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
    public Result getArticlesFromHome(String from, String keyword, Integer menuId, Integer pageIndex){
         List<ArticleSearch> searchArticles = ((HomeServiceImpl) homeService).searchArticles(from, keyword, menuId,
            pageIndex, false, false);
        return ResultPack.fluent(searchArticles);
    }

    /**
     * 获取所有菜单
     * @return Result数据体
     */
    @GetMapping({"menu"})
    public Result getMenus(){
        // 获取"闲言碎语"小组件
        List<CoreMenu> menus = homeService.getMenusToTree();
        return ResultPack.fluent(menus);
    }

    /**
     * 分页获取"闲言碎语"小组件数据
     * @return Result数据体
     */
    @GetMapping({"gossip/{pageIndex}"})
    public Result getGossips(@PathVariable Integer pageIndex, Integer pageCount){
        // 获取"闲言碎语"小组件
        List<CoreGossip> gossips = homeService.getGossipBySlide(pageIndex, pageCount);
        return ResultPack.fluent(gossips);
    }
}
