package top.reminisce.coolnetblogcore.service.home.abstractBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.elastic.ArticleSearchRepository;
import top.reminisce.coolnetblogcore.service.home.HomeArticleQueryService;
import top.reminisce.coolnetblogcore.util.ValidationUtils;

import java.util.List;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.SEARCH_ACTION_FROM_KEYWORD;
import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.SEARCH_ACTION_FROM_MENU;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public abstract class AbstractHomeArticleQuery extends AbstractHomeQuery implements HomeArticleQueryService {

    /**
     * ArticleSearch数据访问层 -> elastic based
     */
    @Autowired
    protected ArticleSearchRepository articleSearchRepository;

    /**
     * 获取文章分页数据，而不是文章详情。<br>
     * 采用elasticsearch存储文章检索的数据。但具体文章详情存储于mysql，参见"获取文章详情"。
     * @param from 前台网页查询文章的来源动作：<br>
     * menu 点击检索了某菜单<br>
     * keyword 点击了搜索框<br>
     * 若不存在列表中，则是不带任何来源的文章分页<br>
     */
    public List<ArticleSearch> searchArticles(String from, String keyword, Integer menuId, Integer pageIndex){
        ValidationUtils.searchArticlePramsCheck(from, keyword, menuId);
        // 从配置中获取设置的每页文章条数
        Integer pageCountValue = super.adminRepository.getSettingPageCountValue(super.beanUtils.getMongoTemplate());
        ValidationUtils.pagePramsCheck(pageIndex, pageCountValue);
        // 开始根据动作来源检索文章数据。从elasticsearch
        return dealArticlePageDataByFrom(from, keyword, menuId, pageIndex, pageCountValue);
    }

    /**
     * 处理文章分页核心方法
     */
    private List<ArticleSearch> dealArticlePageDataByFrom(String from, String keyword, Integer menuId,
                                                          Integer pageIndex, Integer pageCountValue) {
        List<ArticleSearch> articleSearches = null;
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageIndex - 1, pageCountValue, sort);
        // 不带任何来源的文章分页
        if (ObjectUtils.isEmpty(from)){
            articleSearches = this.articleSearchRepository.findAll(pageable).getContent();
        }
        // 点击检索了某菜单
        if (from.equals(SEARCH_ACTION_FROM_MENU)){
            articleSearches = this.articleSearchRepository.findByMenuId(menuId, pageable);
        }
        // 点击了搜索框
        if (from.equals(SEARCH_ACTION_FROM_KEYWORD)){
             this.articleSearchRepository.fuzzinessSearch(super.beanUtils.getElasticsearchRestTemplate(),
                keyword, pageable);
        }
        return articleSearches;
    }

    @Override
    public List<CoreArticle> getAllArticles() {
        return null;
    }

    @Override
    public CoreArticle getArticleById(Integer id) {
        return null;
    }
}
