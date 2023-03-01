package top.reminisce.coolnetblogcore.service.admin;

import top.reminisce.coolnetblogcore.pojo.dto.LoginDto;
import top.reminisce.coolnetblogcore.pojo.dto.ResetPasswordDto;
import top.reminisce.coolnetblogcore.pojo.po.mongo.CoreSysAdmin;
import top.reminisce.coolnetblogcore.service.BaseService;

/**
 * 用于后台管理员登录、注销等操作服务层
 * @author BlueSky
 * @date 2022/10/10
 */
public interface AdminActionStatusService extends BaseService {
    /**
     * 登录操作 当前用户
     * @return
     */
    Object loginAction(LoginDto loginDto);
    /**
     *注销操作 当前用户
     * 根据当前请求上下文注销当前请求线程SecurityContext中存储的当前用户
     * @return
     */
     Object logoutAction();

    /**
     * 重置密码操作 当前用户
     * @return
     */
    Object resetAction(ResetPasswordDto resetPasswordDto);
}
