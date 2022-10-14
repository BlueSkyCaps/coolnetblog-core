package top.reminisce.coolnetblogcore.service.admin.abstractBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.config.springsecurity.LoginUserInfo;
import top.reminisce.coolnetblogcore.repository.elastic.ArticleSearchRepository;
import top.reminisce.coolnetblogcore.repository.sql.ArticleMapper;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeQueryService;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
public abstract class AbstractAdminService extends AbstractHomeQueryService {
    /**
     * ArticleSearch数据访问层 -> elastic based
     */
    @Autowired
    protected ArticleSearchRepository articleSearchRepository;

    /**
     * Article数据访问层 -> sql based
     */
    @Autowired
    protected ArticleMapper articleMapper;

    public static LoginUserInfo securityContextPrincipal() {
        return ((LoginUserInfo) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

}
