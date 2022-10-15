package top.reminisce.coolnetblogcore.service.admin.impl;

import joptsimple.internal.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.handler.exception.BlogNotExistExceptionTips;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.admin.AdminArticleSaveService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;
import top.reminisce.coolnetblogcore.util.TextStringUtils;
import top.reminisce.coolnetblogcore.util.TimeUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 专门用于保存文章的服务实现类
 * @author BlueSky
 * @date 2022/10/13
 */
@Service
public class AdminArticleSaveServiceImpl extends AbstractHomeArticleQueryService implements AdminArticleSaveService {

    /**
     * 菜单数据访问层 -> sql based
     */
    protected final MenuMapper menuMapper;



    public AdminArticleSaveServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;

    }

    @Override
    public CoreArticle saveArticleWheel(CoreArticle article) {
        initArticleLogic(article);
        if (ObjectUtils.isEmpty(article.getId()) || article.getId()==0){
            super.articleMapper.insert(article);
            return article;
        }
        super.articleMapper.updateById(article);
        return article;
        // todo 同步更新文章到elastic
    }

    private void initArticleLogic(CoreArticle article) {
        if (ObjectUtils.isEmpty(article.getId()) || article.getId()==0){
            article.setCreateTime(TimeUtils.currentDateTime());
        }else {
            initArticleExistLogic(article.getId());
        }
        article.setUpdateTime(TimeUtils.currentDateTime());
        Optional<CoreMenu> menu = Optional.ofNullable(this.menuMapper.selectById(article.getMenuId()));
        if (! menu.isPresent()){
            throw new BlogNotExistExceptionTips("保存文章：对应菜单已不存在，请刷新");
        }
        if (menu.get().getIsHome()){
            throw new BlogException("不能将文章归类到主页菜单下。");
        }
        if (Optional.ofNullable(super.getArticleByCustUri(article.getCustUri())).isPresent()){
            throw new BlogNotExistExceptionTips("文章的自定义uri已存在，请更换。");
        }
        if (article.getIsLock() && ! StringUtils.hasText(article.getLockPassword())){
            throw new BlogException("隐私文章必须设置加锁密码。");
        }
        // 处理设置的关键词，多个关键词规定用" "分隔
        if (StringUtils.hasText(article.getLabels())){
            List<String> strings = Arrays.stream(article.getLabels().split(TextStringUtils.SPACE_VALUE))
                .filter(s->! s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
            article.setLabels(Strings.join(strings, TextStringUtils.SPACE_VALUE));
        }

    }

    private void initArticleExistLogic(Integer id){
        if (! Optional.ofNullable(super.getArticleById(id)).isPresent()){
            throw new BlogNotExistExceptionTips("文章已不存在，请刷新。");
        }
    }
    @Override
    public void removeArticle(Integer id) {
        initArticleExistLogic(id);
        super.articleMapper.deleteById(id);
        // todo 删除文章关联评论，在mongodb; 同步删除文章搜索实体，在elastic
    }
}
  