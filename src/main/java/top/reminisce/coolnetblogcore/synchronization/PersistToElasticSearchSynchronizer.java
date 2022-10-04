package top.reminisce.coolnetblogcore.synchronization;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
public interface PersistToElasticSearchSynchronizer extends BlogSynchronizer{
    /**
     * mysql中的文章实体全量新增到elasticsearch索引中<br/>
     * 应该用于初始化迁移数据至elasticsearch索引。若数据量庞大将耗时间，不应该在线上业务中执行此方法。
     * @return true 完成；false，引发错误。
     */
    boolean articlesInsertInFullSync();
}
