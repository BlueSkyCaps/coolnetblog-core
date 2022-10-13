package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.*;
import top.reminisce.coolnetblogcore.service.BaseService;

/**
 * 后台文章服务层
 * @author BlueSky
 * @date 2022/10/1
 */
public interface AdminArticleSaveService extends BaseService {

    /**
     * 更新文章
     * @param article 文章内容
     * @return 更新后的文章实体
     */
    CoreArticle updateArticle(CoreArticle article);

    /**
     * 新增文章
     * @param article 文章内容
     * @return 新增后的文章实体
     */
    CoreArticle addArticle(CoreArticle article);

    /**
     * 删除文章
     * @param id 文章id
     */
    void removeArticle(Integer id);
}
