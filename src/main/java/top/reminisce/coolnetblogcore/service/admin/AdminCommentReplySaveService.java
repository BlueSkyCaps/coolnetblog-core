package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.service.BaseService;

/**
 * 后台评论胡服务层
 * @author BlueSky
 * @date 2022/10/1
 */
public interface AdminCommentReplySaveService extends BaseService {


    /**
     * 后台管理员新增评论
     * @param comment 评论数据
     * @return 新增后的评论
     */
    CoreComment addCommentByAdmin(CoreComment comment);
    /**
     * 删除某条评论及其所有回复
     * @param id 评论id
     */
    void removeComment(String id);

    /**
     * 后台管理员新增回复
     * @param reply 回复数据
     * @return 新增后的回复
     */
    CoreReply addReplyByAdmin(CoreReply reply);
    /**
     * 删除某条回复
     * @param id 回复id
     */
    void removeReply(String id);

    /**
     * 删除某内容（文章）的所有关联评论以及评论关联的所有回复。<br/>
     * 此方法通常在删除文章时被联动执行。
     * @param sourceId 要删除的内容id，如文章id
     * @param sourceType 内容类型，如文章为1
     */
    void removeSourceRelated(Integer sourceId, Integer sourceType);
}
