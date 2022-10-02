package top.reminisce.coolnetblogcore.pojo.ao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteSetting {
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
    private Integer onePageCount;
    private String loginUriValue;
    private String loveLookTitle;
    private String wishPictureRelPath;
    private boolean isShowWishPicture;
    private String wishPictureName;
    private boolean isOpenDetailThumb;
    private Integer leaveLimitCount;
    private boolean isShowLeaveHeadImg;
    private boolean isShowGossip;
}
