package top.reminisce.coolnetblogcore.service.admin;

import org.springframework.web.multipart.MultipartFile;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
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
    CoreSysAdmin saveSetting(CoreSysAdmin sysAdmin);

    /**
     * 更新菜单或新增菜单  -> based sql
     * @param menu 菜单数据
     */
    CoreMenu saveMenuWheel(CoreMenu menu);

    /**
     * 删除菜单 -> based sql
     * @param menuId 菜单id
     */
    void removeMenu(Integer menuId);

    /**
     * 添加一条"闲言碎语"记录 -> based sql
     * @param gossip "闲言碎语"数据
     */

    void addGossip(CoreGossip gossip);


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
    void addFilePath(CoreFilePath filePath, MultipartFile upFile);

    /**
     * 删除文件对应记录 -> based sql
     * @param id 对应文件的id
     */
    void removeFilePath(Integer id);

    /**
     * 新增"看看这些"数据，链接数据 -> based sql
     * @param loveLook 看看这些"数据
     */
    void addLoveLook(CoreLoveLook loveLook);

    /**
     * 删除"看看这些"数据，某条链接 -> based sql
     * @param id 链接数据id
     */
    void removeLoveLook(Integer id);
}
