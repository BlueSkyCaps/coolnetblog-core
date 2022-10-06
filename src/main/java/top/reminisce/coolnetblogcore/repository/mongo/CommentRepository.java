package top.reminisce.coolnetblogcore.repository.mongo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.reminisce.coolnetblogcore.common.BlogException;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;

import java.util.List;
import java.util.Objects;

/**
 * Comment数据访问层 处理评论 -> mongo based
 * @author BlueSky
 * @date 2022/10/2
 */

public interface CommentRepository extends MongoRepository<CoreComment, Integer> {
    /**
     * 获取指定原内容id的评论，使用分页
     * @param sourceId 源内容id（文章id...）
     * @param sourceType 源内容类型（通常文章是1）
     * @param page 分页对象
     * @return 分页获取到的评论列表
     */
    List<CoreComment> findBySourceIdAndSourceType(Integer sourceId, Integer sourceType, Pageable page);
}
