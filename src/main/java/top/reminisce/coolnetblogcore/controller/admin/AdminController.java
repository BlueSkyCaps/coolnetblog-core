package top.reminisce.coolnetblogcore.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.ao.SiteSetting;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.dto.*;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.admin.AdminArticleSaveService;
import top.reminisce.coolnetblogcore.service.admin.AdminSaveService;
import top.reminisce.coolnetblogcore.service.admin.impl.AdminArticleSaveServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理员操作接口。此接口通常用于管理员删除或保存某具体数据，不包括登录注销。
 * 没有另外的情形下，查询操作也不包含，而是使用前台控制器。
 * @author BlueSky
 * @date 2022/10/10
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminSaveService adminSaveService;

    /**
     * 后台删除一条“闲言碎语”
     */
    @DeleteMapping({"gossip/{id}"})
    public Result removeGossip(@PathVariable Integer id){
        this.adminSaveService.removeGossip(id);
        return ResultPack.fluent("删除成功。");
    }

    /**
     * 后台新增一条“闲言碎语”
     */
    @PostMapping({"gossip"})
    public Result addGossip(@RequestBody @Valid GossipAddDto gossipAddDto){
        this.adminSaveService.addGossip(gossipAddDto);
        return ResultPack.fluent("新增成功。");
    }


    /**
     * 后台保存站点配置
     */
    @PutMapping({"site/setting"})
    public Result saveAdminSetting(@RequestBody @Valid SiteSettingDto siteSettingDto){
        return ResultPack.fluent(this.adminSaveService.saveSiteSetting(siteSettingDto));
    }

    /**
     * 后台删除菜单
     */
    @DeleteMapping({"menu/{id}"})
    public Result removeMenu(@PathVariable Integer id){
        this.adminSaveService.removeMenu(id);
        return ResultPack.fluent("删除成功。");
    }

    /**
     * 后台保存菜单
     */
    @PostMapping({"menu"})
    public Result saveMenu(@RequestBody @Valid MenuDto menuDto){
        return ResultPack.fluent(this.adminSaveService.saveMenuWheel(menuDto));
    }

    /**
     * 后台上传文件
     * @param filePathDto 对应文件信息
     * @param upFile 上传的文件
     */
    @PostMapping({"upload"})
    public Result addFilePath(@RequestPart @Valid FilePathDto filePathDto, @RequestPart MultipartFile upFile){
        this.adminSaveService.addFilePath(filePathDto, upFile);
        return ResultPack.fluent("上传成功。");
    }

    /**
     * 删除文件对应记录
     */
    @DeleteMapping({"upload/{id}"})
    public Result removeFilePath(@PathVariable Integer id){
        this.adminSaveService.removeFilePath(id);
        return ResultPack.fluent("删除成功。");
    }

    /**
     * 新增"看看这些"数据
     * @param loveLookAddDto 看看这些"数据
     */
    @PostMapping({"love-look"})
    public Result addLoveLook(@RequestBody @Valid LoveLookAddDto loveLookAddDto){
        this.adminSaveService.addLoveLook(loveLookAddDto);
        return ResultPack.fluent("新增成功。");
    }

    /**
     * 删除"看看这些"数据
     * @param id 链接数据id
     */
    @DeleteMapping({"love-look/{id}"})
    public Result removeLoveLook(@PathVariable Integer id){
        this.adminSaveService.removeLoveLook(id);
        return ResultPack.fluent("删除成功。");
    }
}
