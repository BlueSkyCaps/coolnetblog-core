package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author BlueSky
 * @date 2022/10/6
 */
@Getter
@Setter
@NoArgsConstructor
@NotNull
public class ArticleDto {
    private Integer id;
    @NotNull(message = "请指定菜单")
    private Integer menuId;
    @NotEmpty(message = "标题不得为空")
    @Length(max = 50, message = "标题超过50字符")
    private String title;
    @NotEmpty(message = "主体内容不得为空")
    @Length(max = 10000, message = "主体内容超过一万字符")
    private String content;
    @NotNull
    private Boolean isShowTitle;
    @NotNull
    @NotEmpty(message = "摘要不得为空")
    @Length(max = 10000, message = "摘要超过300字符")
    private String abstractMsg;
    @NotNull
    private Boolean isLock;
    private String lockPassword;
    @NotNull
    private Boolean isDraft;
    private String labels;
    private String custUri;
    @NotNull(message = "请指定评论文章的类型")
    private Integer commentType;
    @NotNull
    private Boolean isSpecial;
}
