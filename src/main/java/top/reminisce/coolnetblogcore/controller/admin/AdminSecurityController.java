package top.reminisce.coolnetblogcore.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.dto.LoginDto;
import top.reminisce.coolnetblogcore.pojo.dto.ResetPasswordDto;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.admin.AdminActionStatusService;

import javax.validation.Valid;

/**
 * @author BlueSky
 * @date 2022/10/10
 */
@RestController
@RequestMapping("admin")
public class AdminSecurityController {

    @Autowired
    private AdminActionStatusService adminActionStatusService;

    /**
     * 登陆验证接口 若登录成功，返回token
     * @return Result数据体 包含token
     */
    @PostMapping({"login"})
    public Result adminLogin(@RequestBody @Valid LoginDto loginDto){
        return ResultPack.fluent(this.adminActionStatusService.loginAction(loginDto));
    }

    /**
     * 退出登录接口 注销
     * @return Result数据体
     */
    @PostMapping({"logout"})
    public Result adminLogout(){
        return ResultPack.fluent("已注销。", this.adminActionStatusService.logoutAction());
    }

    /**
     * 重置密码接口
     * @return Result数据体
     */
    @PostMapping({"reset"})
    public Result adminReset(ResetPasswordDto dto){
        return ResultPack.fluent("已重置。", this.adminActionStatusService.resetAction(dto));
    }
}
