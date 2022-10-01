package top.reminisce.coolnetblogcore.mapper.home;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.reminisce.coolnetblogcore.pojo.vo.Result;

/**
 * 用于前台界面展示所需处理的数据访问层接口
 * @author BlueSky
 * @date 2022/10/1
 */
@Mapper
public interface HomeMapper<T> extends BaseMapper<T> {
    T getGlobalData();
}
