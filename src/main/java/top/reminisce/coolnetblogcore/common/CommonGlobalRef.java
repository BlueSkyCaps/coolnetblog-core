package top.reminisce.coolnetblogcore.common;

import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;

import javax.print.attribute.standard.Finishings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 提供全局访问的数据
 * @author BlueSky
 * @date 2022/10/2
 */

public class CommonGlobalRef {

    public static final String SEARCH_ACTION_FROM_MENU = "menu";
    public static final String SEARCH_ACTION_FROM_KEYWORD = "keyword";
    /**
     * 前台网页查询文章的来源动作：
     * menu 点击检索了某菜单
     * keyword 点击了搜索框
     * 若不存在列表中，则是不带任何来源的文章分页
     */
    public static List<String> searchArticleActionValues = Arrays.asList(SEARCH_ACTION_FROM_MENU, SEARCH_ACTION_FROM_KEYWORD);

    /**
     * 可被关键词模糊匹配搜索的ArticleSearch文章文档的字段
     */
    public static final String[] ARTICLE_SEARCH_ABLE_FIELD_NAMES = new String[]{"title", "content", "abstractMsg", "labels"};

    /**
     * 文章文档索引名
     */
    public static final String ARTICLE_SEARCH_INDEX_NAME = ArticleSearch.class.getAnnotation(
        org.springframework.data.elasticsearch.annotations.Document.class).indexName();
}

