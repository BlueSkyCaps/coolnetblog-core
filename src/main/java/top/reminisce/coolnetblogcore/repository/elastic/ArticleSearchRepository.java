package top.reminisce.coolnetblogcore.repository.elastic;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.exception.BlogException;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ARTICLE_SEARCH_ABLE_FIELD_NAMES;
import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.ARTICLE_SEARCH_INDEX_NAME;

/**
 * ArticleSearch数据访问层 -> elastic based
 * @author BlueSky
 * @date 2022/10/3
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleSearch, String> {

    static void initCheck(ElasticsearchRestTemplate template, ArticleSearch articleDoc){
        if (Objects.isNull(template)) {
            throw new BlogException("ElasticsearchRestTemplate必须不为null！");
        }
        if (Objects.isNull(articleDoc)) {
            throw new BlogException("要插入的文章文档必须不为null！");
        }
    }
    /**
     * 获取属于MenuId的文章列表，采用分页。<br>
     * 约束仓储
     * @param menuId 指定menuId
     * @param pageable 分页对象
     * @return 列表
     */
    List<ArticleSearch> findByMenuId(Integer menuId, Pageable pageable);
    /**
     * 插入一条文章文档。（若id存在则会更新）
     * @param template ElasticsearchRestTemplate对象
     * @param articleDoc 文档
     * @return 生成的id
     */
    default String insertOne(ElasticsearchRestTemplate template, ArticleSearch articleDoc) {
        initCheck(template, articleDoc);
        IndexQuery indexDocumentQuery = new IndexQueryBuilder()
            .withId(articleDoc.getId())
            .withObject(articleDoc).build();
        return template
            .index(indexDocumentQuery, IndexCoordinates.of(ARTICLE_SEARCH_INDEX_NAME));
    }

    /**
     * 插入多条文章文档。（若id存在则会更新）
     * @param template ElasticsearchRestTemplate对象
     * @param articleDocs 文档列表
     * @return 生成的文档对象的信息列表
     */
    default List<IndexedObjectInformation> insertMany(ElasticsearchRestTemplate template, Set<ArticleSearch> articleDocs) {
        if (Objects.isNull(template)) {
            throw new BlogException("ElasticsearchRestTemplate必须不为null！");
        }
        if (ObjectUtils.isEmpty(articleDocs)) {
            throw new BlogException("要插入的文章文档列表必须不为空！");
        }
        List<IndexQuery> indexDocumentQueries = articleDocs.stream()
            .map(a -> new IndexQueryBuilder()
                .withId(a.getId())
                .withObject(a)
                .build())
            .collect(Collectors.toList());
        return template.bulkIndex(indexDocumentQueries, IndexCoordinates.of(ARTICLE_SEARCH_INDEX_NAME));
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
