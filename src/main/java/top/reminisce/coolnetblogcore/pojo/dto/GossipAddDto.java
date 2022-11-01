package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author BlueSky
 * @date 2022/10/6
 */
@Getter
@Setter
@NoArgsConstructor
@NotNull
public class GossipAddDto {
    @NotNull(message = "请传递type值")
    private Integer type;
    @NotEmpty(message = "内容请填写。")
    private String content;
    private String imgUrl;
}
