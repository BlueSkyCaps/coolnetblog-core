package top.reminisce.coolnetblogcore.pojo.ao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFieldMap;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Getter
@Setter
@NoArgsConstructor
public class GlobalEachNeedData {
    /**
     * 菜单数据
     */
    private CoreMenu menu;
    /**
     * "看看这些"组件数据
     */
    private CoreLoveLook loveLook;
    /**
     * 字典映射值
     */
    private CoreFieldMap fieldMap;
}
