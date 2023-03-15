package top.reminisce.coolnetblogcore.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.handler.exception.BlogNotExistExceptionTips;
import top.reminisce.coolnetblogcore.pojo.ao.SiteSetting;
import top.reminisce.coolnetblogcore.pojo.dto.*;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.repository.sql.FilePathMapper;
import top.reminisce.coolnetblogcore.repository.sql.GossipMapper;
import top.reminisce.coolnetblogcore.repository.sql.LoveLookMapper;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.admin.AdminSaveService;
import top.reminisce.coolnetblogcore.util.PathUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;
import top.reminisce.coolnetblogcore.util.mapperConvert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

/**
 * @author BlueSky
 * @date 2022/10/13
 */
@Service
public class AdminSaveServiceImpl extends AdminQueryServiceImpl implements AdminSaveService {

    public AdminSaveServiceImpl(MenuMapper menuMapper, LoveLookMapper loveLookMapper, GossipMapper gossipMapper, FilePathMapper filePathMapper) {
        super(menuMapper, loveLookMapper, gossipMapper, filePathMapper);
    }

    // todo 缓存 更新
    @Override
    public CoreSysAdmin saveSysAdmin(CoreSysAdmin sysAdmin) {
        return super.adminRepository.save(sysAdmin);
    }

    @Override
    public CoreSysAdmin saveSiteSetting(SiteSettingDto siteSettingDto) {
        SiteSetting siteSetting = SiteSettingDtoToSiteSettingMapperUtils.INSTANCE
            .siteSettingDtoToSiteSettingMapperUtils(siteSettingDto);
        // 从当前SecurityContext获取当前的用户信息
        CoreSysAdmin sysAdmin = super.getSysAdmin(securityContextPrincipal().getUsername());
        sysAdmin.setSiteSetting(siteSetting);
        return this.saveSysAdmin(sysAdmin);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public CoreMenu saveMenuWheel(MenuDto menuDto) {
        CoreMenu menu = MenuDtoToMenuMapperUtils.INSTANCE.menuDtoToMenu(menuDto);
        if (! Objects.isNull(menu.getId()) && menu.getId() > 0) {
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
     *
     * @param menu 要保存的菜单
     */
    private void initSaveMenuLogic(CoreMenu menu) {
        CoreMenu mObj;
        if (menu.getIsHome() && menu.getPid() != 0) {
            throw new BlogException("主页菜单必须是顶级菜单。");
        }
        if (!ObjectUtils.isEmpty(menu.getPid()) && menu.getPid() != 0) {
            mObj = super.menuMapper.selectById(menu.getPid());
            if (Objects.isNull(mObj)) {
                throw new BlogException("该菜单设置了上级菜单，但已不存在，请刷新。");
            }
            if (mObj.getIsHome()) {
                throw new BlogException("该菜单设置了上级菜单，但上级菜单属于主页菜单，主页菜单不能有子菜单。");
            }
        }
        Wrapper<CoreMenu> wrapper1 = new LambdaQueryWrapper<CoreMenu>().eq(CoreMenu::getName, menu.getName());
        if (super.menuMapper.exists(wrapper1)) {
            throw new BlogException("菜单名称已经存在");
        }
        menu.setUpdateTime(TimeUtils.currentDateTime());
        // 若设置为主页菜单，则需要把之前的主页菜单重置
        if (menu.getIsHome()) {
            Wrapper<CoreMenu> wrapper2 = new LambdaQueryWrapper<CoreMenu>().eq(CoreMenu::getIsHome, true);
            CoreMenu homeMenu = super.menuMapper.selectOne(wrapper2);
            homeMenu.setIsHome(false);
            super.menuMapper.updateById(homeMenu);
        }
    }

    /**
     * 验证删除菜单逻辑
     *
     * @param menu 要删除的菜单
     */
    private void initRemoveMenuLogic(CoreMenu menu) {
        Wrapper<CoreMenu> wrapper = new LambdaQueryWrapper<CoreMenu>().eq(CoreMenu::getPid, menu.getId());
        if (super.menuMapper.exists(wrapper)) {
            throw new BlogException("该菜单还有子菜单，不允许删除。");
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void removeMenu(Integer menuId) {
        initRemoveMenuLogic(menuExistLogic(menuId));
        // 执行删除
        super.menuMapper.deleteById(menuId);

    }

    private CoreMenu menuExistLogic(Integer menuId) {
        CoreMenu menu1 = super.menuMapper.selectById(menuId);
        if (Objects.isNull(menu1)) {
            throw new BlogNotExistExceptionTips("该菜单已不存在，请刷新。");
        }
        return menu1;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addGossip(GossipAddDto gossipAddDto) {
        CoreGossip gossip = GossipAddDtoToGossipMapperUtils.INSTANCE.gossipAddDtoToGossip(gossipAddDto);
        if (gossip.getType()==2 && ! StringUtils.hasText(gossip.getImgUrl())){
            throw new BlogException("添加一条闲言碎语：带图片的文本请传递图片地址");
        }
        gossip.setAddTime(TimeUtils.currentDateTime());
        super.gossipMapper.insert(gossip);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void removeGossip(Integer id) {
        if (Objects.isNull(super.gossipMapper.selectById(id))) {
            throw new BlogNotExistExceptionTips("删除的项已不存在。");
        }
        super.gossipMapper.deleteById(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addFilePath(FilePathDto filePathDto, MultipartFile upFile) {
        CoreFilePath filePathObj = FilePathDtoToFilePathMapperUtils.INSTANCE.filePathDtoToFilePath(filePathDto);
        boolean exists = super.filePathMapper.exists(
            new LambdaQueryWrapper<CoreFilePath>().eq(CoreFilePath::getHelpName, filePathObj.getHelpName()));
        if (exists) {
            throw new BlogNotExistExceptionTips("要上传的文件资源的名称已经存在。");
        }
        String saveAbsPathName = PathUtils.getCurrentProjectSourcePath();
        String dbRefPath;
        // 若类型是图片 保存到图片静态资源路径
        if (filePathObj.getFileType() == 1) {
            String nowDateDir = TimeUtils.getDateTimeTextUsePatten(TimeUtils.currentDateTime(), "yyyyMMdd");
            dbRefPath = File.separator + Paths.get("static", "img", nowDateDir, filePathObj.getHelpName());
        } else {
            // 是非图片类型的文件
            dbRefPath = File.separator + Paths.get("static", "link", filePathObj.getHelpName());
        }
        saveAbsPathName = Paths.get(saveAbsPathName, dbRefPath).toString();
        // 截取文件类型后缀
        String suffix = PathUtils.getFileNameSuffix(upFile.getOriginalFilename());
        String saveFilePath = saveAbsPathName+"."+suffix;
        if (Objects.isNull(suffix)){
            throw new BlogException("你必须上传有明确类型后缀的文件。");
        }
        super.filePathMapper.insert(filePathObj);
        File fileObj = new File(saveFilePath);
        try {
            upFile.transferTo(fileObj);
        } catch (IOException e) {
            throw new RuntimeException("保存文件到资源路径失败。"+e);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void removeFilePath(Integer id) {
        CoreFilePath filePath = super.filePathMapper.selectById(id);
        if (!Optional.ofNullable(filePath).isPresent()) {
            throw new BlogNotExistExceptionTips("要删除的文件已不存在。");
        }
        // 组合实际文件绝对路径
        String absPath = Paths.get(PathUtils.getCurrentProjectStaticResourcesPath(), filePath.getFileRelPath()).toString();
        super.filePathMapper.deleteById(id);
        boolean delete = new File(absPath).delete();
        if (! delete){
            throw new RuntimeException("删除文件失败，此文件无法被删除。参见 java.io.File#delete");
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addLoveLook(LoveLookAddDto loveLookAddDto) {
        CoreLoveLook loveLook = LoveLookAddDtoToLoveLookMapperUtils.INSTANCE.loveLookAddDtoToLoveLookMapperUtils(loveLookAddDto);
        CoreSysAdmin setting = super.getSysAdmin(securityContextPrincipal().getUsername());
        String domain = setting.getSiteSetting().getDomain();
        if (!StringUtils.hasText(domain)) {
            throw new BlogException("检测到未设置域名信息，请到‘站点设置’中填写你的服务器域名");
        }
        String link;
        //1内部文章链接2上传的文件链接3外部链接
        switch (loveLook.getLinkType()) {
            case 1:
                link = File.separator + Paths.get("detail", loveLook.getRelHref());
                break;
            case 2:
                link = File.separator + Paths.get("static", "link", loveLook.getRelHref());
                break;
            case 3:
                link = loveLook.getRelHref();
                break;
            default:
                throw new BlogException("添加“看看这些”链接失败，链接类型没有得到匹配。");
        }
        loveLook.setRelHref(link);
        loveLook.setAddTime(TimeUtils.currentDateTime());
        super.loveLookMapper.insert(loveLook);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void removeLoveLook(Integer id) {
        if (Objects.isNull(super.loveLookMapper.selectById(id))) {
            throw new BlogNotExistExceptionTips("删除的链接已不存在。");
        }
        super.loveLookMapper.deleteById(id);
    }
}
  