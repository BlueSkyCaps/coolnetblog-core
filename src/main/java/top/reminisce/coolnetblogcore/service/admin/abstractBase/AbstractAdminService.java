package top.reminisce.coolnetblogcore.service.admin.abstractBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.repository.elastic.ArticleSearchRepository;
import top.reminisce.coolnetblogcore.repository.mongo.SysAdminRepository;
import top.reminisce.coolnetblogcore.repository.sql.ArticleMapper;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeArticleQueryService;
import top.reminisce.coolnetblogcore.service.home.abstractBase.AbstractHomeQueryService;
import top.reminisce.coolnetblogcore.util.bean.SpringBeanUtils;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
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

}
