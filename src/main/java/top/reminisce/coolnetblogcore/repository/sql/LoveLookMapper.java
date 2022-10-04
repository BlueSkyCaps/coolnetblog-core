package top.reminisce.coolnetblogcore.repository.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;

/**
 * “看看这些”数据访问层 -> sql based
 * @author BlueSky
 * @date 2022/10/1
 */
@Mapper
public interface LoveLookMapper extends BaseMapper<CoreLoveLook> {
}
