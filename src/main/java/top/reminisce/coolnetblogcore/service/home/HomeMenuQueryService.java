package top.reminisce.coolnetblogcore.service.home;

import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.service.BaseService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeMenuQueryService extends BaseService {
    List<CoreMenu> getAllMenus();
    List<CoreMenu> getMenusToTree();
    CoreMenu getMenuById(@NotNull Integer id);
}
