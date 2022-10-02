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
public abstract class AbstractHomeQuery implements HomeArticleQueryService {

    /**
     * SysAdmin数据访问层
     */
    @Autowired
    private SysAdminRepository adminRepository;
    /**
     * 获取spring注入的bean的工具类
     */
    @Autowired
    private  SpringBeanUtils beanUtils;

    public List<CoreArticle> searchArticles(String from, String keyword, Integer pageIndex){
        ValidationUtils.searchArticlePramsCheck(from, keyword);
        Integer pageCountValue = adminRepository.getSettingPageCountValue(beanUtils.getMongoTemplate());
        // 从配置中获取设置的每页文章条数
        ValidationUtils.pagePramsCheck(pageIndex, pageCountValue);
        // 开始根据动作来源检索文章数据。从elasticsearch
        return null;
    }

    @Override
    public List<CoreArticle> getAllArticles() {
        return null;
    }

    @Override
    public CoreArticle getArticleById(Integer id) {
        return null;
    }
}
