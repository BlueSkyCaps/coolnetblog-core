package top.reminisce.coolnetblogcore.pojo.po.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import top.reminisce.coolnetblogcore.pojo.ao.AdminDetail;
import top.reminisce.coolnetblogcore.pojo.ao.SiteSetting;

import java.io.Serializable;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Document(collection  = "core_sys_admin")
@Getter
@Setter
public class CoreSysAdmin implements Serializable {
    @Id
    private String id;
    private String accountName;
    private String password;
    private String token;
    private AdminDetail adminDetail;
    private SiteSetting siteSetting;
}

