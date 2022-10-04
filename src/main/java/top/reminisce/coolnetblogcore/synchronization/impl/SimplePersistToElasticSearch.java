package top.reminisce.coolnetblogcore.synchronization.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.elastic.ArticleSearchRepository;
import top.reminisce.coolnetblogcore.service.admin.AdminService;
import top.reminisce.coolnetblogcore.service.admin.ArticleService;
import top.reminisce.coolnetblogcore.synchronization.PersistToElasticSearchSynchronizer;
import top.reminisce.coolnetblogcore.util.bean.SpringBeanUtils;
import top.reminisce.coolnetblogcore.util.convert.ArticleToArticleSearchMapperUtils;

import java.util.*;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public class SimplePersistToElasticSearch implements PersistToElasticSearchSynchronizer {
    @Autowired
    private ArticleSearchRepository articleSearchRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    protected SpringBeanUtils beanUtils;

    @Override
    public boolean articlesInsertInFullSync(){
        List<CoreArticle> viewAbleArticles = adminService.getAllViewAbleArticles();
        Set<ArticleSearch> insertArticleSearches = new LinkedHashSet<>();
        for (CoreArticle article : viewAbleArticles) {
            ArticleSearch articleSearch = ArticleToArticleSearchMapperUtils.INSTANCE.articleToArticleSearch(article);
            insertArticleSearches.add(articleSearch);
        }
        articleSearchRepository.insertMany(beanUtils.getElasticsearchRestTemplate(), insertArticleSearches);
        return true;
    }

}
