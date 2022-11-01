package top.reminisce.coolnetblogcore.util.mapperConvert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.reminisce.coolnetblogcore.pojo.dto.CommentAddDto;
import top.reminisce.coolnetblogcore.pojo.dto.GossipAddDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreComment;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;

/**
 * @author BlueSky
 * @date 2022/10/4
 */
@Mapper
public interface GossipAddDtoToGossipMapperUtils {

    static GossipAddDtoToGossipMapperUtils INSTANCE = Mappers.getMapper( GossipAddDtoToGossipMapperUtils.class );

    CoreGossip gossipAddDtoToGossip(GossipAddDto gossipAddDto);
}