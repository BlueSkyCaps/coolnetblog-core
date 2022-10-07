package top.reminisce.coolnetblogcore.pojo.po.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author BlueSky
 * @date 2022/10/7
 */
@Document(collection  = "core_thumb_up")
@Getter
@Setter
public class CoreThumbUp {
    @Id
    private String id;
    private String clientBrowser;
    private String clientDevice;
    private String clientIp;
    private Integer sourceId;
    private Integer sourceType;
    private Integer thumbType;
    private Date upTime;
}
