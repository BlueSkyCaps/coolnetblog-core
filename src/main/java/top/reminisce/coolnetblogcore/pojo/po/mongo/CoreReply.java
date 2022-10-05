package top.reminisce.coolnetblogcore.pojo.po.mongo;

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
@Document(collection  = "core_reply")
@Getter
@Setter
public class CoreReply implements Serializable {
    @Id
    private Integer id;
    private Integer commentId;
    private String name;
    private String siteUrl;
    private String email;
    private String content;
    private boolean isPassed;
    private Date replyTime;
    private boolean isAdmin;
    private String clientIp;
    private String clientDevice;
    private String clientBrowser;
}
