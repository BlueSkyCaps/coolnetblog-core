package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.dto.ArticleDto;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface ArticleDtoToArticleMapperUtils {

    static ArticleDtoToArticleMapperUtils INSTANCE = Mappers.getMapper( ArticleDtoToArticleMapperUtils.class );
    CoreArticle articleDtoToArticleMapperUtils(ArticleDto articleDto);
}