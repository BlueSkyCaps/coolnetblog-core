package top.reminisce.coolnetblogcore.pojo.ao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFieldMap;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;

import java.util.List;

/**
 * 在客户端首次加载首页时，返回首次加载所需的数据体。
 * @author BlueSky
 * @date 2022/10/1
 */
@Getter
@Setter
@NoArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GlobalEachNeedData {
    /**
     * 菜单数据
     */
    private List<CoreMenu> menu;
    /**
     * "看看这些"组件数据
     */
    private List<CoreLoveLook> loveLook;
    /**
     * “闲言碎语”组件数据
     */
    private List<CoreGossip> gossip;
    /**
     * 字典映射值
     */
    private List<CoreFieldMap> fieldMap;
    /**
     * 用户配置
     */
    private CoreSysAdmin sysAdmin;
}
