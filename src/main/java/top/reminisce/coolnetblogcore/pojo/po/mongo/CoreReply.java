package top.reminisce.coolnetblogcore.pojo.po.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
    private String id;
    private String commentId;
    private String name;
    private String siteUrl;
    private String email;
    private String content;
    private boolean isPassed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    /*
    * 设置时间格式，返回前端东八区北京时间。mongodb存储时间date为ISO，java实体获取按本地时间处理，
    因此内部仍是正确的本地时间（东八区）。但返回json时，仍会显示ISO格式，所以指定此@JsonFormat
    *  */
    private Date replyTime;
    private boolean isAdmin;
    private String clientIp;
    private String clientDevice;
    private String clientBrowser;
}
