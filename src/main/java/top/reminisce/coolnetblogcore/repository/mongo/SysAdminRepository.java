package top.reminisce.coolnetblogcore.repository.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;

import java.util.Objects;

/**
 * SysAdmin数据访问层 -> mongo based
 * @author BlueSky
 * @date 2022/10/2
 */

public interface SysAdminRepository extends MongoRepository<CoreSysAdmin, String> {

    /**
     * 获取默认的管理员和站点配置数据，过滤指定字段。<br/>
     * 通常用于返回给前端。
     * @param template MongoTemplate对象
     * @param fields CoreSysAdmin中要过滤的配置键
     * @return 不包含过滤字段的CoreSysAdmin，默认第一条
     */
     default CoreSysAdmin getAndExclude(MongoTemplate template, String... fields) {
        if (Objects.isNull(template)) {
            throw new BlogException("MongoTemplate必须不为null！");
        }
        Query query = new Query();
        query.fields().exclude(fields);
        return template.findOne(query, CoreSysAdmin.class);
    }
    /**
     * 获取默认的站点配置中的某个设置。
     *
     * @param template MongoTemplate对象
     * @param field    CoreSysAdmin配置键
     * @return 默认第一条CoreSysAdmin对象，但只有指定键被填充值
     */
    default CoreSysAdmin getSettingValue(MongoTemplate template, String field) {
        if (Objects.isNull(template)) {
            throw new BlogException("MongoTemplate必须不为null！");
        }
        Query query = new Query();
        query.fields().include(field);
        return Objects.requireNonNull(template.findOne(query, CoreSysAdmin.class));
    }

    /**
     * 获取管理员和站点配置数据，根据指定的AccountName。<br/>
     * @param accountName 账户名，用于后台登录
     * @return 完整的CoreSysAdmin对象
     */
    CoreSysAdmin findByAccountName(String accountName);

}
