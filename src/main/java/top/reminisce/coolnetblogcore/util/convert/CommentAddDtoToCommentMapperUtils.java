package top.reminisce.coolnetblogcore.util.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.ao.elastic.ArticleSearch;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreArticle;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface CommentAddDtoToCommentMapperUtils {

    static CommentAddDtoToCommentMapperUtils INSTANCE = Mappers.getMapper( CommentAddDtoToCommentMapperUtils.class );

    /**
     * CommentAddDto映射成CoreComment
     * @param commentAddDto 要映射过去的原CommentAddDto
     * @return 映射后的CoreComment
     */
    CoreComment commentAddDtoToComment(CommentAddDto commentAddDto);
}