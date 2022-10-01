package top.reminisce.coolnetblogcore.service.home.impl;

import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.ao.TreeMenu;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.service.home.HomeService;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Service
public class HomeServiceImpl implements HomeService {
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
    public List<TreeMenu> getMenusToTree() {
        return null;
    }

    @Override
    public CoreMenu getMenuById(Integer id) {
        return null;
    }
}
