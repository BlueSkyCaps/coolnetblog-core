package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author BlueSky
 * @date 2022/10/10
 */
@Data
@NotNull(message = "登录数据不得为空")
public class LoginDto {
    @NotEmpty(message = "账户名不得为空")
    private String accountName;
    @NotEmpty(message = "密码不得为空")
    private String password;
}
