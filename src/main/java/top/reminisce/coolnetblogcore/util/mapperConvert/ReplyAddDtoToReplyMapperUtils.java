package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.ReplyAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface ReplyAddDtoToReplyMapperUtils {

    static ReplyAddDtoToReplyMapperUtils INSTANCE = Mappers.getMapper( ReplyAddDtoToReplyMapperUtils.class );

    /**
     * ReplyAddDto映射成CoreReply
     * @param replyAddDto 要映射过去的原ReplyAddDto
     * @return 映射后的CoreReply
     */
    CoreReply replyAddDtoToReply(ReplyAddDto replyAddDto);
}