package top.reminisce.coolnetblogcore.service.home;

import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.service.BaseService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeArticleQueryService extends BaseService {
    /**
     * 根据情况获取文章详情，要么提供了自定义uri，要么就是id
     * @param custUri 文章设置的uri
     * @param id 文章id
     * @return 文章实体
     */
    CoreArticle getDetailBasedQueryContext(String custUri, Integer id);

    /**
     * 获取文章分页数据，而不是文章详情。<br>
     * 采用elasticsearch存储文章检索的数据。但具体文章详情存储于mysql，参见"获取文章详情"。
     * @param from 前台网页查询文章的来源动作：<br>
     * menu 点击检索了某菜单<br>
     * keyword 点击了搜索框<br>
     * 若不存在列表中，则是不带任何来源的文章分页<br>
     * @param keyword 关键词，若提供
     * @param menuId 菜单id，若提供
     * @param pageIndex 页码
     */
    List<ArticleSearch> searchArticles(String from, String keyword, Integer menuId, Integer pageIndex);
}
