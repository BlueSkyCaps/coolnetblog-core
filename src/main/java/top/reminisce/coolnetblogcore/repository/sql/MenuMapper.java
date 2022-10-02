package top.reminisce.coolnetblogcore.repository.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreMenu;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
@Mapper
public interface MenuMapper extends BaseMapper<CoreMenu> {
}
