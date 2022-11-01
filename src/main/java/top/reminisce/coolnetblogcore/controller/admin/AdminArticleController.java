package top.reminisce.coolnetblogcore.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.dto.ArticleDto;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.admin.AdminArticleSaveService;
import top.reminisce.coolnetblogcore.service.admin.impl.AdminArticleSaveServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理员<b>内容文章</b>操作接口。此接口通常用于管理员删除或保存<b>内容文章</b>。
 * 没有另外的情形下，查询操作也不包含，而是使用前台控制器。
 * @author BlueSky
 * @date 2022/10/10
 */
@RestController
@RequestMapping("admin")
public class AdminArticleController {
    @Autowired
    private AdminArticleSaveService adminArticleSaveService;


    /**
     * 后台获取文章分页。<br/>
     * 此接口与前台文章接口一致，但包含草稿。独立封装此接口，因为此接口需要后台认证下请求。<br>
     * 参见
     * {@link top.reminisce.coolnetblogcore.controller.home.HomeController#getArticlesFromHome}
     */
    @GetMapping({"article"})
    public Result getArticlesFromAdmin(String from, String keyword, Integer menuId, Integer pageIndex){
        List<ArticleSearch> searchArticles = ((AdminArticleSaveServiceImpl) adminArticleSaveService)
            .searchArticles(from, keyword, menuId, pageIndex, true);
        return ResultPack.fluent(searchArticles);
    }


    /**
     * 保存文章
     * @param articleDto 文章内容
     */
    @PostMapping("article")
    public Result addArticle(@RequestBody @Valid ArticleDto articleDto){
        CoreArticle article = this.adminArticleSaveService.saveArticleWheel(articleDto);
        return ResultPack.fluent(article);
    }



    /**
     * 删除文章
     * @param id 文章id
     */
    @DeleteMapping("article/{id}")
    public Result removeArticle(@PathVariable Integer id){
        this.adminArticleSaveService.removeArticle(id);
        return ResultPack.fluent("删除成功。");
    }
}
