package top.reminisce.coolnetblogcore.service.home.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.repository.mongo.SysAdminRepository;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.home.HomeService;
import top.reminisce.coolnetblogcore.util.bean.SpringBeanUtils;

import java.util.List;
import java.util.stream.Collectors;

import static top.reminisce.coolnetblogcore.util.StructureUtils.toTree;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Service
public class HomeServiceImpl implements HomeService {
    private final MenuMapper menuMapper;
    private final SysAdminRepository adminRepository;
    private final SpringBeanUtils beanUtils;

    public HomeServiceImpl(MenuMapper menuMapper,SysAdminRepository adminRepository, SpringBeanUtils beanUtils) {
        this.menuMapper = menuMapper;
        this.adminRepository = adminRepository;
        this.beanUtils = beanUtils;
    }

    @Override
    public Object dealGlobalEachNeedData() {
        // 获取菜单
        List<CoreMenu> treeMenus = getMenusToTree();
        // 获取站点配置
        CoreSysAdmin coreSysAdmin = adminRepository.getOneExcludeSecurity(beanUtils.getMongoTemplate());
        /* 小组件 */
        // "看看这些"小组件
        return treeMenus;
    }


    @Override
    public List<CoreFilePath> getAllFilePath() {
        return null;
    }

    @Override
    public CoreFilePath getFilePathById(Integer id) {
        return null;
    }

    @Override
    public List<CoreGossip> getAllGossip() {
        return null;
    }

    @Override
    public List<CoreGossip> getGossipBySlide(Integer index, Integer count) {
        return null;
    }

    @Override
    public CoreGossip getGossipById(Integer id) {
        return null;
    }

    @Override
    public List<CoreLoveLook> getAllLoveLook() {
        return null;
    }

    @Override
    public CoreLoveLook getLoveLookById(Integer id) {
        return null;
    }

    @Override
    public List<CoreMenu> getAllMenus() {
        return null;
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


    @Override
    public CoreMenu getMenuById(Integer id) {
        return null;
    }
}
