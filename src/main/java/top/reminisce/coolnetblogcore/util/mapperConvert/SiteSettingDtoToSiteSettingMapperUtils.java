package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.ao.SiteSetting;
import top.reminisce.coolnetblogcore.pojo.dto.GossipAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.SiteSettingDto;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface SiteSettingDtoToSiteSettingMapperUtils {

    static SiteSettingDtoToSiteSettingMapperUtils INSTANCE = Mappers.getMapper( SiteSettingDtoToSiteSettingMapperUtils.class );

    SiteSetting siteSettingDtoToSiteSettingMapperUtils(SiteSettingDto siteSettingDto);
}