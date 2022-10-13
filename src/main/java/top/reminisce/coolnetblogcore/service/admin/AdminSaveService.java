package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.service.BaseService;
import top.reminisce.coolnetblogcore.service.home.*;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface AdminSaveService extends BaseService {
    /**
     * 保存CoreSysAdmin -> based mongo
     * @param sysAdmin 要保存的CoreSysAdmin
     * @return 新的CoreSysAdmin
     */
    CoreSysAdmin saveSysAdmin(CoreSysAdmin sysAdmin);
}
