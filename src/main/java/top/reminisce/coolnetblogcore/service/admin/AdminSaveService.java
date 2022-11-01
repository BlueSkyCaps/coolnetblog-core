package top.reminisce.coolnetblogcore.service.admin;

import org.springframework.web.multipart.MultipartFile;
import top.reminisce.coolnetblogcore.pojo.dto.*;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.service.BaseService;

/**
 * 基础admin管理员服务层
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

    /**
     * 只保存SiteSitting
     * @param siteSetting 要保存的SiteSettingDto，转为CoreSysAdmin.SiteSitting
     * @return 新的CoreSysAdmin
     */
    CoreSysAdmin saveSiteSetting(SiteSettingDto siteSetting);

    /**
     * 更新菜单或新增菜单  -> based sql
     * @param menu 菜单数据
     */
    CoreMenu saveMenuWheel(MenuDto menu);

    /**
     * 删除菜单 -> based sql
     * @param menuId 菜单id
     */
    void removeMenu(Integer menuId);

    /**
     * 添加一条"闲言碎语"记录 -> based sql
     * @param gossip "闲言碎语"数据
     */

    void addGossip(GossipAddDto gossip);


    /**
     * 删除一条"闲言碎语"记录 -> based sql
     * @param id id
     */
    void removeGossip(Integer id);

    /**
     * 上传文件 新增对应文件信息到持久层 -> based sql
     * @param filePath 对应文件信息
     * @param upFile 上传的文件
     */
    void addFilePath(FilePathDto filePath, MultipartFile upFile);

    /**
     * 删除文件对应记录 -> based sql
     * @param id 对应文件的id
     */
    void removeFilePath(Integer id);

    /**
     * 新增"看看这些"数据，链接数据 -> based sql
     * @param loveLook 看看这些"数据
     */
    void addLoveLook(LoveLookAddDto loveLook);

    /**
     * 删除"看看这些"数据，某条链接 -> based sql
     * @param id 链接数据id
     */
    void removeLoveLook(Integer id);
}
