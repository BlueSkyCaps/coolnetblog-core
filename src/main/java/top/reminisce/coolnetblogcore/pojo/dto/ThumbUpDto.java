package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * @author BlueSky
 * @date 2022/10/7
 */
@Getter
@Setter
@NoArgsConstructor
@NotNull
public class ThumbUpDto {
    @NotNull(message = "前台错误传递：点赞的原内容id不得为空")
    private Integer sourceId;

    @NotNull(message = "前台错误传递：点赞的原内容类型不得为空")
    private Integer sourceType;

    /**
     * 点赞类型 若是文章点赞，此字段不为null。
     */
    private Integer thumbType;
}
