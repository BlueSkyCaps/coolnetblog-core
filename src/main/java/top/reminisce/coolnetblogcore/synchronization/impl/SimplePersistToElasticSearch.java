package top.reminisce.coolnetblogcore.synchronization.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import top.reminisce.coolnetblogcore.service.home.HomeArticleQueryService;
import top.reminisce.coolnetblogcore.synchronization.PersistToElasticSearchSynchronizer;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Component
public class SimplePersistToElasticSearch implements PersistToElasticSearchSynchronizer {
    @Qualifier("abstractAdminArticleService")
    @Autowired
    private HomeArticleQueryService articleQueryService;

}
