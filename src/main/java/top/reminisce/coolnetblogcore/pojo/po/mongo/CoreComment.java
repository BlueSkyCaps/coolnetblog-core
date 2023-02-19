package top.reminisce.coolnetblogcore.pojo.po.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date commentTime;
    private boolean isAdmin;
    private String clientIp;
    private String clientDevice;
    private String clientBrowser;
    /**
     * 对应回复 不存在于评论文档中
     */
    @Transient
    private List<CoreReply> replies;
}
