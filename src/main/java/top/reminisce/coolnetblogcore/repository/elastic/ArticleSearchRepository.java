package top.reminisce.coolnetblogcore.repository.elastic;

import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.util.ObjectUtils;
import top.reminisce.coolnetblogcore.common.BlogException;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;

import java.util.Objects;

/**
 * @author BlueSky
 * @date 2022/10/3
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<ArticleSearch, String> {
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
            .index(indexQuery, IndexCoordinates.of(
                ArticleSearch.class.getAnnotation(
                    org.springframework.data.elasticsearch.annotations.Document.class).indexName()));
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
}
