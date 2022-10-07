package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface ArticleToArticleSearchMapperUtils {

    static ArticleToArticleSearchMapperUtils INSTANCE = Mappers.getMapper( ArticleToArticleSearchMapperUtils.class );
    ArticleSearch articleToArticleSearch(CoreArticle article);
}