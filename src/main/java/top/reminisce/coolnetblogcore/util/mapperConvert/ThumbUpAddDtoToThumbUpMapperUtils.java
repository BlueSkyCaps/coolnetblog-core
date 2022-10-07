package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.dto.ReplyAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.ThumbUpDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreReply;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreThumbUp;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface ThumbUpAddDtoToThumbUpMapperUtils {

    static ThumbUpAddDtoToThumbUpMapperUtils INSTANCE = Mappers.getMapper( ThumbUpAddDtoToThumbUpMapperUtils.class );

    /**
     * ThumbUpDto映射成CoreThumbUp
     * @param thumbUpDto 要映射过去的原ThumbUpDto
     * @return 映射后的CoreThumbUp
     */
    CoreThumbUp thumbUpAddDtoToThumbUp(ThumbUpDto thumbUpDto);
}