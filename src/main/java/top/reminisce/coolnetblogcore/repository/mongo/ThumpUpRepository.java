package top.reminisce.coolnetblogcore.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreThumbUp;
import top.reminisce.coolnetblogcore.repository.BaseMongoTemplateRepository;

/**
 * ThumpUp数据访问层 处理点赞 -> mongo based
 * @author BlueSky
 * @date 2022/10/2
 */

public interface ThumpUpRepository extends MongoRepository<CoreThumbUp, String>, BaseMongoTemplateRepository {

}
