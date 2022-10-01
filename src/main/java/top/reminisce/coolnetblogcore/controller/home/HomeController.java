package top.reminisce.coolnetblogcore.controller.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.vo.Result;

/**
 * 前台界面所需数据的接口访问层
 * @author BlueSky
 * @date 2022/10/1
 */
@RestController
public class HomeController {

    @GetMapping("global")
    public Result getGlobalEachNeedData(){
        return ResultPack.fluent();
    }

}
