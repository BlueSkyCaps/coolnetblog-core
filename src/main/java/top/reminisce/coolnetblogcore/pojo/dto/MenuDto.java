package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

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
public class MenuDto {
    private Integer id;
    @NotEmpty(message = "菜单名不得为空")
    @Length(min = 1, max = 10, message = "菜单名字数为1~10")
    private String name;
    private Integer pid;
    private Boolean isHome;
    @NotNull(message = "顺序号请指定")
    private Integer orderNumber;
    private Boolean isShow;
    private String tips;
}
