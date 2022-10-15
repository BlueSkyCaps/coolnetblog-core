package top.reminisce.coolnetblogcore.service.admin.impl;

import joptsimple.internal.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.handler.exception.BlogNotExistExceptionTips;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.admin.AdminArticleSaveService;
import top.reminisce.coolnetblogcore.service.admin.abstractBase.AbstractAdminService;
import top.reminisce.coolnetblogcore.service.home.HomeArticleQueryService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;
import top.reminisce.coolnetblogcore.service.home.impl.HomeServiceImpl;
import top.reminisce.coolnetblogcore.util.TextStringUtils;

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
        initAddArticleLogic(article);
        return null;
    }

    private void initAddArticleLogic(CoreArticle article) {
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

    @Override
    public void removeArticle(Integer id) {
    }
}
  