package top.reminisce.coolnetblogcore.service.admin;

import org.springframework.beans.factory.annotation.Value;
import top.reminisce.coolnetblogcore.pojo.po.sql.CoreGossip;
import top.reminisce.coolnetblogcore.service.BaseService;
import top.reminisce.coolnetblogcore.service.home.HomeGossipService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BlueSky
 * @date 2022/10/1
 */
public interface GossipService extends HomeGossipService {
}
