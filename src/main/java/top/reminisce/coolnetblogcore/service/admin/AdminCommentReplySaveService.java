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
     * 新增评论
     * @param comment 评论数据
     * @return 新增后的评论
     */
    CoreComment addComment(CoreComment comment);
    /**
     * 删除评论
     * @param sourceId 源内容id
     * @param commentId 评论id
     */
    void removeComment(Integer sourceId, Integer commentId);

    /**
     * 新增回复
     * @param reply 回复数据
     * @return 新增后的回复
     */
    CoreReply addReply(CoreReply reply);
    /**
     * 删除回复
     * @param id 回复id
     * @param commentId 关联评论id
     */
    void removeReply(Integer id, Integer commentId);
}
