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
    List<CoreGossip> getAllGossip();
    List<CoreGossip> getGossipBySlide(@NotNull Integer index,  @Value("10") Integer count);
    CoreGossip getGossipById(@NotNull Integer id);
}
