package top.reminisce.coolnetblogcore.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author BlueSky
 * @date 2022/10/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NotNull(message = "重置数据不得为空")
public class ResetPasswordDto extends LoginDto{
    @NotEmpty(message = "新账户名不得为空")
    private String newAccountName;
    @NotEmpty(message = "新密码不得为空")
    private String newPassword;
}
