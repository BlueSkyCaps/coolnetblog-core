package top.reminisce.coolnetblogcore.repository.elastic;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.common.BlogException;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;

import java.util.List;
import java.util.Objects;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ARTICLE_SEARCH_ABLE_FIELD_NAMES;
import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ARTICLE_SEARCH_INDEX_NAME;

/**
 * ArticleSearch数据访问层 -> elastic based
 * @author BlueSky
 * @date 2022/10/3
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleSearch, String> {

    /**
     * 获取属于MenuId的文章列表，采用分页。<br>
     * 约束仓储
     * @param menuId 指定menuId
     * @param pageable 分页对象
     * @return 列表
     */
    List<ArticleSearch> findByMenuId(Integer menuId, Pageable pageable);
    /**
     * 插入一条文章文档
     * @param template ElasticsearchRestTemplate对象
     * @param articleDoc 文档
     * @return 生成的id
     */
    default String insertOne(ElasticsearchRestTemplate template, ArticleSearch articleDoc) {
        if (Objects.isNull(template)) {
            throw new BlogException("ElasticsearchRestTemplate必须不为null！");
        }
        IndexQuery indexQuery = new IndexQueryBuilder()
            .withId(articleDoc.getId())
            .withObject(articleDoc).build();
        return template
            .index(indexQuery, IndexCoordinates.of(ARTICLE_SEARCH_INDEX_NAME));
    }
    /**
     * 插入一条文章文档，不自动生成id，id主动提供
     * @param template ElasticsearchRestTemplate对象
     * @param articleDoc 文档
     * @param theId 提供的id
     * @return 生成的id
     */
    default String insertOneBySuppliedId(ElasticsearchRestTemplate template, ArticleSearch articleDoc, String theId) {
        if (ObjectUtils.isEmpty(theId)){
            throw new BlogException("不自动生成id，id主动提供，但id为空！");
        }
        articleDoc.setId(theId);
        return insertOne(template, articleDoc);
    }

    /**
     * 关键词搜索文章
     * @param template ElasticsearchRestTemplate
     * @param queryText 关键词文本
     * @param pageable 分页对象
     * @return SearchHits，搜索后封装的结果
     */
    default SearchHits<ArticleSearch> fuzzinessSearch(ElasticsearchRestTemplate template, String queryText, Pageable pageable) {
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(queryText, ARTICLE_SEARCH_ABLE_FIELD_NAMES)
            .fuzziness(Fuzziness.AUTO);

        Query q = new NativeSearchQueryBuilder()
            .withFilter(queryBuilder)
            .withPageable(pageable)
            .build();
        return template.search(q, ArticleSearch.class, IndexCoordinates.of(ARTICLE_SEARCH_INDEX_NAME));
    }
}
