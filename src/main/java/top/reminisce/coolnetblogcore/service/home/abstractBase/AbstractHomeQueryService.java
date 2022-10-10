package top.reminisce.coolnetblogcore.service.home.abstractBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.repository.mongo.SysAdminRepository;
import top.reminisce.coolnetblogcore.util.bean.SpringBeanUtils;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public abstract class AbstractHomeQueryService {
    /**
     * SysAdmin数据访问层 -> Mongo based
     */
    @Autowired
    protected SysAdminRepository adminRepository;
    /**
     * 获取spring注入的bean的工具类
     */
    @Autowired
    protected  SpringBeanUtils beanUtils;

    /**
     * 获取管理员和站点配置数据，过滤隐私安全字段。
     * @return 不包含隐私字段的CoreSysAdmin
     */
    // todo 缓存 查
    protected CoreSysAdmin getSettingExcludeSecurity() {
        return this.adminRepository.getAndExclude(this.beanUtils.getMongoTemplate(),
            "password", "token", "adminDetail");
    }

    /**
     * 获取完整的管理员和站点配置数据，根据accountName<br/>
     * 此数据不会返回给前端，而是用于登录效验。
     */
    // todo 缓存 查
    protected CoreSysAdmin getSetting(String accountName) {
        return this.adminRepository.findByAccountName(accountName);
    }
}
