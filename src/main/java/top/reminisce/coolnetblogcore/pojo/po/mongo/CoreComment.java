package top.reminisce.coolnetblogcore.pojo.po.mongo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author BlueSky
 * @date 2022/10/5
 */
@Document(collection  = "core_comment")
@Getter
@Setter
public class CoreComment implements Serializable {
    @Id
    private Integer id;
    private Integer sourceId;
    private Integer sourceType;
    private String name;
    private String siteUrl;
    private String email;
    private String content;
    private boolean isPassed;
    private Date commentTime;
    private boolean isAdmin;
    private String clientIp;
    private String clientDevice;
    private String clientBrowser;
}
