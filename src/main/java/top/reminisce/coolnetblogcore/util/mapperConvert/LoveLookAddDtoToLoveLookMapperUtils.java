package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.LoveLookAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface LoveLookAddDtoToLoveLookMapperUtils {

    static LoveLookAddDtoToLoveLookMapperUtils INSTANCE = Mappers.getMapper( LoveLookAddDtoToLoveLookMapperUtils.class );

    CoreLoveLook loveLookAddDtoToLoveLookMapperUtils(LoveLookAddDto loveLookAddDto);
}