package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author BlueSky
 * @date 2022/11/1
 */
@Getter
@Setter
@NotNull(message = "站点配置不得为空")
public class SiteSettingDto {
    @NotEmpty(message = "站点名不得为空")
    private String siteName;
    @NotEmpty(message = "ip地址不得为空")
    private String host;
    private String domain;
    private String fashionQuotes;
    private boolean isShowSiteName;
    private boolean isShowEdgeSearch;
    private boolean isShowLoveLook;
    private boolean isShowQutoes;
    private String cban;
    private String tailContent;
    @Range(min = 1, max = 100, message = "每页显示的内容数为1~100")
    @NotNull(message = "每页显示的内容数为1~100")
    private Integer onePageCount;
    private String loginUriValue;
    private String loveLookTitle;
    private String wishPictureRelPath;
    private boolean isShowWishPicture;
    private String wishPictureName;
    private boolean isOpenDetailThumb;
    @Range(max = 100, message = "每日限制留言数为0~100")
    @NotNull(message = "每日限制留言数为0~100")
    private Integer leaveLimitCount;
    private boolean isShowLeaveHeadImg;
    private boolean isShowGossip;
}
