package top.reminisce.coolnetblogcore.service.home;

import top.reminisce.coolnetblogcore.pojo.po.sql.CoreFieldMap;
import top.reminisce.coolnetblogcore.service.BaseService;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface HomeFieldMapQueryService extends BaseService {
    List<CoreFieldMap> getAllFieldMap();
}
