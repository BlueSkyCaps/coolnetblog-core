package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.dto.ArticleDto;
import top.reminisce.coolnetblogcore.pojo.po.sql.*;
import top.reminisce.coolnetblogcore.service.BaseService;

/**
 * 后台文章服务层
 * @author BlueSky
 * @date 2022/10/1
 */
public interface AdminArticleSaveService extends BaseService {

    /**
     * 新增或更新文章
     * @param article 文章内容
     * @return 保存后的文章实体
     */
    CoreArticle saveArticleWheel(ArticleDto article);

    /**
     * 删除文章
     * @param id 文章id
     */
    void removeArticle(Integer id);
}
