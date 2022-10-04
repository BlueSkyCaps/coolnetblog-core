package top.reminisce.coolnetblogcore.service.home;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeGlobalNeedService extends HomeMenuQueryService, HomeGossipQueryService, HomeLoveLookQueryService {
    /**
     * 在客户端首次加载首页时，返回首次加载所需的数据体。
     * 通常包括树形结构的导航栏菜单、小组件数据、前台配置，以及字段映射值。
     * 但此方法不包含文章数据。请见获取文章数据接口。
     * @return 所需的数据
     */
    Object dealGlobalEachNeedData();
}
