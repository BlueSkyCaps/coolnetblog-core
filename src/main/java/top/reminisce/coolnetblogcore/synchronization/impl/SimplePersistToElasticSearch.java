package top.reminisce.coolnetblogcore.synchronization.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.elastic.ArticleSearchRepository;
import top.reminisce.coolnetblogcore.service.home.HomeArticleQueryService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;
import top.reminisce.coolnetblogcore.synchronization.PersistToElasticSearchSynchronizer;
import top.reminisce.coolnetblogcore.util.bean.SpringBeanUtils;
import top.reminisce.coolnetblogcore.util.mapperConvert.ArticleToArticleSearchMapperUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * mysql同步elasticsearch的简单实现类
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public class SimplePersistToElasticSearch implements PersistToElasticSearchSynchronizer {
    @Autowired
    private ArticleSearchRepository articleSearchRepository;
    @Qualifier("homeServiceImpl")
    @Autowired
    private HomeArticleQueryService homeArticleQueryService;
    @Autowired
    protected SpringBeanUtils beanUtils;

    @Override
    public boolean articlesInsertInFullSync(){
        List<CoreArticle> viewAbleArticles = ((HomeServiceImpl)homeArticleQueryService).articleMapper.selectList(null);
        Set<ArticleSearch> insertArticleSearches = new LinkedHashSet<>();
        for (CoreArticle article : viewAbleArticles) {
            ArticleSearch articleSearch = ArticleToArticleSearchMapperUtils.INSTANCE.articleToArticleSearch(article);
            insertArticleSearches.add(articleSearch);
        }
        articleSearchRepository.saveMany(beanUtils.getElasticsearchRestTemplate(), insertArticleSearches);
        return true;
    }

    @Override
    public boolean articleInsertSync(CoreArticle article){
        ArticleSearch articleSearch = ArticleToArticleSearchMapperUtils.INSTANCE.articleToArticleSearch(article);
        articleSearchRepository.saveOne(beanUtils.getElasticsearchRestTemplate(), articleSearch);
        return true;
    }
}
