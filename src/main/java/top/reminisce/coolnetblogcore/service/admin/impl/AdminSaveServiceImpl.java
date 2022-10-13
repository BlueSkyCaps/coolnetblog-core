package top.reminisce.coolnetblogcore.service.admin.impl;

import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
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
    public CoreSysAdmin saveSetting(CoreSysAdmin sysAdmin) {
        return super.adminRepository.save(sysAdmin);
    }

    @Override
    public CoreMenu updateMenu(CoreMenu menu) {
        return null;
    }

    @Override
    public CoreMenu addMenu(CoreMenu menu) {
        return null;
    }

    @Override
    public void removeMenu(Integer menuId) {

    }

    @Override
    public CoreGossip addGossip(CoreGossip gossip) {
        return null;
    }

    @Override
    public void removeGossip(Integer id) {

    }

    @Override
    public CoreFilePath addFilePath(CoreFilePath filePath) {
        return null;
    }

    @Override
    public void removeFilePath(Integer id) {

    }

    @Override
    public CoreLoveLook addLoveLook(CoreLoveLook loveLook) {
        return null;
    }

    @Override
    public void removeLoveLook(Integer id) {

    }
}
