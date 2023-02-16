package top.reminisce.coolnetblogcore.pojo.ao.elastic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

/**
 * 用于检索的属于elastic博客索引存储的文章文档。保存文章实体的用于查询的字段，它不是持久层数据库中的真正实体。
 * @author BlueSkyCarry
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Document(indexName="core_blog_article", createIndex=true)
public class ArticleSearch {
    /**
     * mysql文章id，而不是自动生成的文档id
     */
    @Id
    private String id;

    private Integer menuId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Boolean)
    private Boolean isShowTitle;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String abstractMsg;

    @Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss")
    private Date createTime;

    @Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss")
    private Date updateTime;

    @Field(type = FieldType.Boolean)
    private Boolean isLock;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String labels;

    @Field(type = FieldType.Keyword)
    private String custUri;

    @Field(type = FieldType.Boolean)
    private Boolean isSpecial;

    @Field(type = FieldType.Boolean)
    private Boolean isDraft;
}