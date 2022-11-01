package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
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
public class LoveLookAddDto {
    @NotEmpty(message = "链接类型需要传递")
    private Integer linkType;
    @Length(min = 1, max = 20, message = "链接名称不得为空，且不得大于20字符")
    private String linkName;
}
