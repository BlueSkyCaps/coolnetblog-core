package top.reminisce.coolnetblogcore.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.*;
import top.reminisce.coolnetblogcore.repository.sql.GossipMapper;
import top.reminisce.coolnetblogcore.repository.sql.LoveLookMapper;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.admin.AdminQueryService;
import top.reminisce.coolnetblogcore.service.admin.abstractBase.AbstractAdminService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;

import java.util.List;

/**
 * 基础admin后台服务
 * @author BlueSky
 * @date 2022/10/1
 */
@Service
public class AdminQueryServiceImpl extends AbstractAdminService implements AdminQueryService {

    /**
     * 菜单数据访问层 -> sql based
     */
    protected final MenuMapper menuMapper;
    /**
     * "看看这些"小组件数据访问层 -> sql based
     */
    private final LoveLookMapper loveLookMapper;
    /**
     * "闲言碎语"小组件数据访问层 -> sql based
     */
    private final GossipMapper gossipMapper;

    @Autowired
    @Qualifier("abstractHomeArticleQueryService")
    private  AbstractHomeArticleQueryService articleQueryService;

    public AdminQueryServiceImpl(MenuMapper menuMapper, LoveLookMapper loveLookMapper, GossipMapper gossipMapper) {
        this.menuMapper = menuMapper;
        this.loveLookMapper = loveLookMapper;
        this.gossipMapper = gossipMapper;
    }

    @Override
    public List<CoreArticle> getAllViewAbleArticles() {
        Wrapper<CoreArticle> qw = new LambdaQueryWrapper<CoreArticle>()
            .eq(CoreArticle::getIsDraft, false)
            .orderByDesc(CoreArticle::getUpdateTime);
        return this.articleMapper.selectList(qw);
    }

    /**
     * 搜索文章 后台管理员 复用前台搜索文章接口
     * @param includeDraft 包含草稿，当后台管理员查询文章时，此值应该为true
     * @return 包含草稿的文章，分页
     */
    public List<ArticleSearch> searchArticlesIncludeAll(String from, String keyword, Integer menuId, Integer pageIndex,
                                              boolean includeDraft){
        return this.articleQueryService.searchArticles(from, keyword, menuId, pageIndex, includeDraft);
    }
}
