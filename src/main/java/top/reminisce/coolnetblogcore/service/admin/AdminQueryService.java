package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.service.BaseService;
import top.reminisce.coolnetblogcore.service.home.*;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface AdminQueryService extends BaseService {
    /**
     * 获取可被客户端查看到的所有文章，不包括草稿和特俗文章。从mysql中获取，而不是elasticsearch
     */
    @Deprecated
    List<CoreArticle> getAllViewAbleArticles();


}
