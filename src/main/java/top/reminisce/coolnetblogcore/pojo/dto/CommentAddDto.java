package top.reminisce.coolnetblogcore.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author BlueSky
 * @date 2022/10/6
 */
@Getter
@Setter
@NoArgsConstructor
@NotNull
public class CommentAddDto {
    @NotNull(message = "前台错误传递：评论的原内容id不得为空")
    private Integer sourceId;
    @NotNull(message = "前台错误传递：评论的原内容类型不得为空")
    private Integer sourceType;
    @NotEmpty(message = "别忘记输入昵称~")
    @Size(min = 2, max = 20, message = "昵称大于2字符小于20字符哦~")
    private String name;
    @URL(message = "请输入有效的url")
    private String siteUrl;
    @NotEmpty(message = "记得输入邮箱哦~")
    @Email(message = "请输入有效的邮箱~")
    private String email;
    @NotEmpty(message = "记得输入内容吖~")
    @Size(min = 5, max = 200, message = "内容大于5字符小于200字符哦~")
    private String content;
}
