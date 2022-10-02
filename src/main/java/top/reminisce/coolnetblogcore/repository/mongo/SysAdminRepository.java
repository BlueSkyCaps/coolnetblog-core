package top.reminisce.coolnetblogcore.repository.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.reminisce.coolnetblogcore.common.BlogException;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;

import java.util.Objects;

/**
 * @author BlueSky
 * @date 2022/10/2
 */

public interface SysAdminRepository extends MongoRepository<CoreSysAdmin, String> {

    /**
     * 获取管理员和站点配置数据，过滤隐私安全字段。
     * @param template MongoTemplate对象
     * @return 不包含隐私字段的CoreSysAdmin
     */
    default CoreSysAdmin getOneExcludeSecurity(MongoTemplate template) {
        if (Objects.isNull(template)) {
            throw new BlogException("MongoTemplate必须不为null！");
        }
        Query query = new Query();
        query.fields().exclude("password", "token", "adminDetail");
        return template.findOne(query, CoreSysAdmin.class);
    }
}
