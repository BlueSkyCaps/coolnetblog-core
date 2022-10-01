package top.reminisce.coolnetblogcore.service.home;

import top.reminisce.coolnetblogcore.pojo.po.sql.CoreLoveLook;
import top.reminisce.coolnetblogcore.service.BaseService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeLoveLookService extends BaseService {
    List<CoreLoveLook> getAllLoveLook();
    CoreLoveLook getLoveLookById(@NotNull Integer id);
}
