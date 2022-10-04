package top.reminisce.coolnetblogcore.service.home;

import org.springframework.beans.factory.annotation.Value;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.service.BaseService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeGossipQueryService extends BaseService {
    /**
     * 根据分页获取，"闲言碎语"组件数据
     */
    List<CoreGossip> getGossipBySlide(@NotNull Integer index,  @Value("10") Integer count);
}
