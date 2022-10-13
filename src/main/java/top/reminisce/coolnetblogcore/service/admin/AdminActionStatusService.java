package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.dto.LoginDto;
import top.reminisce.coolnetblogcore.pojo.dto.ResetPasswordDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;

/**
 * 用于后台管理员登录、注销等操作服务层
 * @author BlueSky
 * @date 2022/10/10
 */
public interface AdminActionStatusService{
    /**
     * 登录操作
     * @return
     */
    Object loginAction(LoginDto loginDto);
    /**
     *注销操作
     * @return
     */
    Object logoutAction();
    /**
     * 重置密码操作
     * @return
     */
    Object resetAction(ResetPasswordDto resetPasswordDto);
}
