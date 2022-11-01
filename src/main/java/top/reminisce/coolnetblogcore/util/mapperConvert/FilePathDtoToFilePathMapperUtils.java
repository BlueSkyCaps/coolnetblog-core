package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.dto.FilePathDto;
import top.reminisce.coolnetblogcore.pojo.dto.MenuDto;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface FilePathDtoToFilePathMapperUtils {

    static FilePathDtoToFilePathMapperUtils INSTANCE = Mappers.getMapper( FilePathDtoToFilePathMapperUtils.class );

    CoreFilePath filePathDtoToFilePath(FilePathDto filePathDto);
}