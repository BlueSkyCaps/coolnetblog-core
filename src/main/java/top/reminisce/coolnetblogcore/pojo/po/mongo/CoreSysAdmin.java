package top.reminisce.coolnetblogcore.pojo.po.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
@Document(collection  = "core_sys_admin")
public class CoreSysAdmin implements Serializable {
    @Id
    private String id;
    private String accountName;
    private String password;
    private String token;
    private AdminDetail adminDetail;
    private SiteSetting siteSetting;
}
class AdminDetail {
    private String email;
    private String emailPassword;
    private String smtpHost;
    private int smtpPort;
    private boolean smtpIsUseSsl;
}
class SiteSetting{
    private String siteName;
    private String host;
    private String domain;
    private String fashionQuotes;
    private boolean isShowSiteName;
    private boolean isShowEdgeSearch;
    private boolean isShowLoveLook;
    private boolean isShowQutoes;
    private String cban;
    private String tailContent;
    private String onePageCount;
    private String loginUriValue;
    private String loveLookTitle;
    private String wishPictureRelPath;
    private boolean isShowWishPicture;
    private String wishPictureName;
    private boolean isOpenDetailThumb;
    private String leaveLimitCount;
    private boolean isShowLeaveHeadImg;
    private boolean isShowGossip;
}