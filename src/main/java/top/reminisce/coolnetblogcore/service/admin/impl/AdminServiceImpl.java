package top.reminisce.coolnetblogcore.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.*;
import top.reminisce.coolnetblogcore.repository.sql.GossipMapper;
import top.reminisce.coolnetblogcore.repository.sql.LoveLookMapper;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.admin.AdminService;
import top.reminisce.coolnetblogcore.service.admin.abstractBase.AbstractAdminService;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Service
public class AdminServiceImpl extends AbstractAdminService implements AdminService {

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


    public AdminServiceImpl(MenuMapper menuMapper, LoveLookMapper loveLookMapper, GossipMapper gossipMapper) {

        this.menuMapper = menuMapper;
        this.loveLookMapper = loveLookMapper;
        this.gossipMapper = gossipMapper;
    }

    @Override
    public List<CoreFieldMap> getAllFieldMap() {
        return null;
    }

    @Override
    public List<CoreGossip> getGossipBySlide(Integer index, Integer count) {
        return null;
    }

    @Override
    public List<CoreLoveLook> getAllLoveLook() {
        return null;
    }

    @Override
    public List<CoreMenu> getMenusToTree() {
        return null;
    }

    @Override
    public CoreArticle getDetailBasedQueryContext(String custUri, Integer id) {
        return null;
    }

    @Override
    public List<ArticleSearch> searchArticles(String from, String keyword, Integer menuId, Integer pageIndex) {
        return null;
    }

    @Override
    public List<CoreArticle> getAllViewAbleArticles() {
        Wrapper<CoreArticle> qw = new LambdaQueryWrapper<CoreArticle>()
            .eq(CoreArticle::getIsDraft, false)
            .orderByDesc(CoreArticle::getUpdateTime);
        return this.articleMapper.selectList(qw);
    }
}
