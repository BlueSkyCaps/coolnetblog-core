package top.reminisce.coolnetblogcore.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.handler.exception.BlogNotExistExceptionTips;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.*;
import top.reminisce.coolnetblogcore.repository.sql.GossipMapper;
import top.reminisce.coolnetblogcore.repository.sql.LoveLookMapper;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.admin.AdminSaveService;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.text.ParseException;
import java.util.Objects;

/**
 * @author BlueSky
 * @date 2022/10/13
 */
@Service
public class AdminSaveServiceImpl extends AdminQueryServiceImpl implements AdminSaveService {

    public AdminSaveServiceImpl(MenuMapper menuMapper, LoveLookMapper loveLookMapper, GossipMapper gossipMapper) {
        super(menuMapper, loveLookMapper, gossipMapper);
    }

    // todo 缓存 更新
    @Override
    public CoreSysAdmin saveSetting(CoreSysAdmin sysAdmin) {
        return super.adminRepository.save(sysAdmin);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public CoreMenu saveMenuWheel(CoreMenu menu) {
        if (Objects.isNull(menu.getId()) && menu.getId() >0){
            return updateMenu(menu);
        }
        return addMenu(menu);
    }
    
    private CoreMenu updateMenu(CoreMenu menu) {
        menuExistLogic(menu.getId());
        initSaveMenuLogic(menu);
        // 执行保存
        super.menuMapper.updateById(menu);
        return super.menuMapper.selectById(menu.getId());
    }

    private CoreMenu addMenu(CoreMenu menu) {
        initSaveMenuLogic(menu);
        // 执行保存
        super.menuMapper.insert(menu);
        // 新增菜单，自增id封装在实体中
        return super.menuMapper.selectById(menu.getId());
    }

    /**
     * 验证保存菜单逻辑
     * @param menu 要保存的菜单
     */
    private void initSaveMenuLogic(CoreMenu menu) {
        CoreMenu mObj;
        if (menu.getIsHome() && menu.getPid() != 0){
            throw new BlogException("主页菜单必须是顶级菜单。");
        }
        if (! ObjectUtils.isEmpty(menu.getPid()) && menu.getPid() != 0){
            mObj = super.menuMapper.selectById(menu.getPid());
            if (Objects.isNull(mObj)){
                throw new BlogException("该菜单设置了上级菜单，但已不存在，请刷新。");
            }
            if (mObj.getIsHome()){
                throw new BlogException("该菜单设置了上级菜单，但上级菜单属于主页菜单，主页菜单不能有子菜单。");
            }
        }
        Wrapper<CoreMenu> wrapper1 = new LambdaQueryWrapper<CoreMenu>().eq(CoreMenu::getName, menu.getName());
        if (super.menuMapper.exists(wrapper1)){
            throw new BlogException("菜单名称已经存在");
        }
        try {
            menu.setUpdateTime(TimeUtils.currentDateTime());
        } catch (ParseException e) {
            throw new BlogException("保存菜单，设置更新时间失败。"+e.getMessage());
        }
        // 若设置为主页菜单，则需要把之前的主页菜单重置
        if (menu.getIsHome()){
            Wrapper<CoreMenu> wrapper2 = new LambdaQueryWrapper<CoreMenu>().eq(CoreMenu::getIsHome, true);
            CoreMenu homeMenu = super.menuMapper.selectOne(wrapper2);
            homeMenu.setIsHome(false);
            super.menuMapper.updateById(homeMenu);
        }
    }

    /**
     * 验证删除菜单逻辑
     * @param menu 要删除的菜单
     */
    private void initRemoveMenuLogic(CoreMenu menu) {
        Wrapper<CoreMenu> wrapper = new LambdaQueryWrapper<CoreMenu>().eq(CoreMenu::getPid, menu.getId());
        if (super.menuMapper.exists(wrapper)){
            throw new BlogException("该菜单还有子菜单，不允许删除。");
        }
    }

    @Override
    public void removeMenu(Integer menuId) {
        initRemoveMenuLogic(menuExistLogic(menuId));
        // 执行删除
        super.menuMapper.deleteById(menuId);

    }

    private CoreMenu menuExistLogic(Integer menuId) {
        CoreMenu menu1 = super.menuMapper.selectById(menuId);
        if (Objects.isNull(menu1)){
            throw new BlogNotExistExceptionTips("该菜单已不存在，请刷新。");
        }
        return menu1;
    }

    @Override
    public CoreGossip addGossip(CoreGossip gossip) {
        return null;
    }

    @Override
    public void removeGossip(Integer id) {

    }

    @Override
    public CoreFilePath addFilePath(CoreFilePath filePath) {
        return null;
    }

    @Override
    public void removeFilePath(Integer id) {

    }

    @Override
    public CoreLoveLook addLoveLook(CoreLoveLook loveLook) {
        return null;
    }

    @Override
    public void removeLoveLook(Integer id) {

    }
}
