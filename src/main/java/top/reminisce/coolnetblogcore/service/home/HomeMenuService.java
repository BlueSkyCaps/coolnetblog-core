package top.reminisce.coolnetblogcore.service.home;

import top.reminisce.coolnetblogcore.pojo.ao.TreeMenu;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.service.BaseService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeMenuService extends BaseService {
    List<CoreMenu> getAllMenus();
    List<TreeMenu> getMenusToTree();
    CoreMenu getMenuById(@NotNull Integer id);
}
