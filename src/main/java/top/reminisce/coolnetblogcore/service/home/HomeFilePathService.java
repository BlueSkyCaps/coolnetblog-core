package top.reminisce.coolnetblogcore.service.home;

import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFilePath;
import top.reminisce.coolnetblogcore.service.BaseService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeFilePathService extends BaseService {
    List<CoreFilePath> getAllFilePath();
    CoreFilePath getFilePathById(@NotNull Integer id);
}
