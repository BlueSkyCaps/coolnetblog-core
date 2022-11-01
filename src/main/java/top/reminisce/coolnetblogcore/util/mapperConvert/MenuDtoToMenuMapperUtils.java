package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.dto.GossipAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.MenuDto;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface MenuDtoToMenuMapperUtils {

    static MenuDtoToMenuMapperUtils INSTANCE = Mappers.getMapper( MenuDtoToMenuMapperUtils.class );

    CoreMenu menuDtoToMenu(MenuDto menuDto);
}