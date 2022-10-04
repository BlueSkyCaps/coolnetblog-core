package top.reminisce.coolnetblogcore.service.home.abstractBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
}
