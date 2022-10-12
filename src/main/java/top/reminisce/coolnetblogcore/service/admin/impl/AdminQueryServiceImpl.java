package top.reminisce.coolnetblogcore.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.ReplyAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.sql.*;
import top.reminisce.coolnetblogcore.repository.sql.GossipMapper;
import top.reminisce.coolnetblogcore.repository.sql.LoveLookMapper;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.admin.AdminQueryService;
import top.reminisce.coolnetblogcore.service.admin.abstractBase.AbstractAdminService;

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public CoreArticle getDetailBasedQueryContext(String custUri, Integer id) {
        return null;
    }

    @Override
    public List<ArticleSearch> searchArticles(String from, String keyword, Integer menuId, Integer pageIndex) {
        return null;
    }

    @Override
    public List<CoreComment> getCommentsCarryRepliesBySourceIdBasedSlide(Integer sourceId, Integer sourceType, Integer index, Integer commentCount, Integer replyCount) {
        return null;
    }

    @Override
    public List<CoreReply> getAllRepliesByCommentId(Integer commentId) {
        return null;
    }

    @Override
    public List<CoreReply> getRepliesByCommentIdBasedSlide(Integer commentId, Integer index, Integer count) {
        return null;
    }

    @Override
    public CoreComment addCommentPackProcessor(CommentAddDto commentAddDto, HttpServletRequest request) {
        return null;
    }

    @Override
    public CoreReply addReplyPackProcessor(ReplyAddDto replyAddDto, HttpServletRequest request) {
        return null;
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
}
