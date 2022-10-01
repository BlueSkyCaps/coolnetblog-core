package top.reminisce.coolnetblogcore.pojo.ao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Getter
@Setter
@NoArgsConstructor
public class TreeMenu extends CoreMenu {
    private List<TreeMenu> childMenus;
}
