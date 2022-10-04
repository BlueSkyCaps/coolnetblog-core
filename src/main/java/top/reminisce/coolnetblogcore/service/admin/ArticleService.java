package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.service.BaseService;
import top.reminisce.coolnetblogcore.service.home.HomeArticleQueryService;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface ArticleService extends HomeArticleQueryService{
    /**
     * 获取可被客户端查看到的所有文章，不包括草稿。从mysql中获取，而不是elasticsearch
     * @return
     */
    List<CoreArticle> getAllViewAbleArticles();
}
