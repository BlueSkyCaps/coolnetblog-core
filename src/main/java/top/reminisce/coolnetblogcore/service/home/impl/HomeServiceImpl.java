package top.reminisce.coolnetblogcore.service.home.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.ao.GlobalEachNeedData;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.repository.sql.GossipMapper;
import top.reminisce.coolnetblogcore.repository.sql.LoveLookMapper;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.home.HomeGlobalNeedService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;
import top.reminisce.coolnetblogcore.util.ValidationUtils;

import java.util.List;
import java.util.stream.Collectors;

import static top.reminisce.coolnetblogcore.util.StructureUtils.toTree;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Service
public class HomeServiceImpl extends AbstractHomeArticleQueryService implements HomeGlobalNeedService {
    /**
     * 菜单数据访问层 -> sql based
     */
    private final MenuMapper menuMapper;
    /**
     * "看看这些"小组件数据访问层 -> sql based
     */
    private final LoveLookMapper loveLookMapper;
    /**
     * "闲言碎语"小组件数据访问层 -> sql based
     */
    private final GossipMapper gossipMapper;

    /**
     * 首次加载所需数据体
     */
    private final GlobalEachNeedData globalEachNeedData;


    public HomeServiceImpl(MenuMapper menuMapper, LoveLookMapper loveLookMapper,
                           GossipMapper gossipMapper, GlobalEachNeedData globalEachNeedData) {
        this.menuMapper = menuMapper;
        this.loveLookMapper = loveLookMapper;
        this.gossipMapper = gossipMapper;
        this.globalEachNeedData = globalEachNeedData;
    }

    @Override
    public Object dealGlobalEachNeedData() {
        // 获取菜单
        List<CoreMenu> treeMenus = getMenusToTree();
        // 获取站点配置
        CoreSysAdmin coreSysAdmin = super.getSysAdminExcludeSecurity();
        /* 小组件 */
        if (coreSysAdmin.getSiteSetting().isShowLoveLook()){
            // 获取"看看这些"小组件
            List<CoreLoveLook> allLoveLook = getAllLoveLook();
            globalEachNeedData.setLoveLook(allLoveLook);
        }
        if (coreSysAdmin.getSiteSetting().isShowGossip()){
            // 获取"闲言碎语"小组件 初始化第一页，十条
            List<CoreGossip> gossipsFromInitPage = getGossipBySlide(1, 10);
            globalEachNeedData.setGossip(gossipsFromInitPage);
        }
        globalEachNeedData.setMenu(treeMenus);
        globalEachNeedData.setSysAdmin(coreSysAdmin);
        return globalEachNeedData;
    }


    @Override
    public List<CoreGossip> getGossipBySlide(Integer index, Integer count) {
        ValidationUtils.pagePramsCheck(index, count);
        IPage<CoreGossip> page = new Page<>(index, count);
        Wrapper<CoreGossip> qw = new LambdaQueryWrapper<CoreGossip>().orderByDesc(CoreGossip::getAddTime);
        return this.gossipMapper.selectPage(page, qw).getRecords();
    }

    // todo 缓存 查
    @Override
    public List<CoreLoveLook> getAllLoveLook() {
        return this.loveLookMapper.selectList(new LambdaQueryWrapper<CoreLoveLook>()
            .orderByDesc(CoreLoveLook::getAddTime));
    }

    @Override
    public List<CoreMenu> getMenusToTree() {
        // 查出所有菜单。排除不显示的菜单，并且按设置的OrderNumber升序，但主菜单（IsHome）要位于第一位。
        List<CoreMenu> menus = this.menuMapper.selectList(new LambdaQueryWrapper<CoreMenu>()
                .eq(CoreMenu::getIsShow, true)
                .orderByDesc(CoreMenu::getIsHome)
                .orderByAsc(CoreMenu::getOrderNumber));
        // 过滤，检索顶级菜单
        List<CoreMenu> topMenus = menus.stream().filter(m->m.getPid()==0).collect(Collectors.toList());
        List<CoreMenu> children = menus.stream().filter(m->m.getPid()!=0).collect(Collectors.toList());
        // 递归，为每个顶级菜单形成多级菜单
        return toTree(topMenus, children);
    }
}
