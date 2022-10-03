package top.reminisce.coolnetblogcore.service.home.abstractBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.pojo.ao.GlobalEachNeedData;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;
import top.reminisce.coolnetblogcore.repository.mongo.SysAdminRepository;
import top.reminisce.coolnetblogcore.repository.sql.ArticleMapper;
import top.reminisce.coolnetblogcore.repository.sql.GossipMapper;
import top.reminisce.coolnetblogcore.repository.sql.LoveLookMapper;
import top.reminisce.coolnetblogcore.repository.sql.MenuMapper;
import top.reminisce.coolnetblogcore.service.home.HomeArticleQueryService;
import top.reminisce.coolnetblogcore.util.ValidationUtils;
import top.reminisce.coolnetblogcore.util.bean.SpringBeanUtils;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public abstract class AbstractHomeQuery {
    /**
     * SysAdmin数据访问层
     */
    @Autowired
    protected SysAdminRepository adminRepository;
    /**
     * 获取spring注入的bean的工具类
     */
    @Autowired
    protected  SpringBeanUtils beanUtils;
}
