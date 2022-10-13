package top.reminisce.coolnetblogcore.service.admin.impl;

import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.service.admin.AdminSaveService;
import top.reminisce.coolnetblogcore.service.admin.abstractBase.AbstractAdminService;

/**
 * @author BlueSky
 * @date 2022/10/13
 */
@Service
public class AdminSaveServiceImpl extends AbstractAdminService implements AdminSaveService {
    // todo 缓存 更新
    @Override
    public CoreSysAdmin saveSysAdmin(CoreSysAdmin sysAdmin) {
        return super.adminRepository.save(sysAdmin);
    }
}
