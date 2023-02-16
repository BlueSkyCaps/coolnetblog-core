package top.reminisce.coolnetblogcore.service.home.abstractBase;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogNotExistExceptionTips;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.elastic.ArticleSearchRepository;
import top.reminisce.coolnetblogcore.repository.sql.ArticleMapper;
import top.reminisce.coolnetblogcore.service.home.HomeArticleQueryService;
import top.reminisce.coolnetblogcore.util.ValidationUtils;

import java.util.List;
import java.util.stream.Collectors;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.SEARCH_ACTION_FROM_KEYWORD;
import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.SEARCH_ACTION_FROM_MENU;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
public abstract class AbstractHomeArticleQueryService extends AbstractHomeQueryService implements HomeArticleQueryService {
    /**
     * ArticleSearch数据访问层 -> elastic based
     */
    @Autowired
    protected ArticleSearchRepository articleSearchRepository;

    /**
     * Article数据访问层 -> sql based
     */
    @Autowired
    protected ArticleMapper articleMapper;


    @Override
    public List<ArticleSearch> searchArticles(String from, String keyword, Integer menuId, Integer pageIndex,
                                              boolean includeDraft, boolean includeSpecial){
        ValidationUtils.searchArticlePramsCheck(from, keyword, menuId);
        // 从配置中获取设置的每页文章条数
        Integer pageCountValue = super.getSysAdminExcludeSecurity().getSiteSetting().getOnePageCount();
        ValidationUtils.pagePramsCheck(pageIndex, pageCountValue);
        // 开始根据动作来源检索文章数据。从elasticsearch
        return dealArticlePageDataByFrom(from, keyword, menuId, pageIndex, pageCountValue, includeDraft, includeSpecial);
    }

    /**
     * 处理文章分页核心方法
     */
    private List<ArticleSearch> dealArticlePageDataByFrom(String from, String keyword, Integer menuId,
                                                          Integer pageIndex, Integer pageCountValue,
                                                          boolean includeDraft, boolean includeSpecial) {
        List<ArticleSearch> articleSearches = null;
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(pageIndex - 1, pageCountValue, sort);
        Criteria criteria = new Criteria();
        // 包含草稿文章与否
        if (! includeDraft){
            criteria.and(new Criteria("isDraft").is(false));
        }
        // 包含特殊文章与否
        if (! includeSpecial){
            criteria.and(new Criteria("isSpecial").is(false));
        }
        /* 开始匹配动作来源： */
        // 不带任何来源的文章分页
        if (ObjectUtils.isEmpty(from)){
            Query query = new CriteriaQuery(criteria);
            query.setPageable(pageable);
            articleSearches = this.articleSearchRepository
                .searchAll(super.beanUtils.getElasticsearchRestTemplate(), query).getSearchHits().stream()
                .map(SearchHit::getContent).collect(Collectors.toList());
        }
        // 点击检索了某菜单
        if (! ObjectUtils.isEmpty(from) && from.equals(SEARCH_ACTION_FROM_MENU)){
            // 指定菜单id
            criteria.and("menuId").is(menuId);
            Query query = new CriteriaQuery(criteria);
            query.setPageable(pageable);
            articleSearches = this.articleSearchRepository
                .searchAll(super.beanUtils.getElasticsearchRestTemplate(), query).getSearchHits().stream()
                .map(SearchHit::getContent).collect(Collectors.toList());
        }
        // 点击了搜索框
        if (! ObjectUtils.isEmpty(from) && from.equals(SEARCH_ACTION_FROM_KEYWORD)){
            articleSearches = this.articleSearchRepository.fuzzinessSearch(super.beanUtils.getElasticsearchRestTemplate(),
                keyword, pageable).getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        }
        return articleSearches;
    }

    @Override
    public CoreArticle getDetailBasedQueryContext(String custUri, Integer id) {
        CoreArticle article;
        if (! ObjectUtils.isEmpty(custUri)){
            article = getArticleByCustUri(custUri);
            if (article == null){
                article = getArticleById(id);
            }
        }else {
            article = getArticleById(id);
        }
        if (article == null){
            throw new BlogNotExistExceptionTips("已获取不到此文章，请刷新。");
        }
        return article;
    }

    // todo 缓存 查
    protected CoreArticle getArticleById(Integer id){
        return this.articleMapper.selectById(id);
    }

    protected CoreArticle getArticleByCustUri(String custUri){
        Wrapper<CoreArticle> wrapper = new LambdaQueryWrapper<CoreArticle>().eq(CoreArticle::getCustUri, custUri);
        return this.articleMapper.selectOne(wrapper);
    }
}
