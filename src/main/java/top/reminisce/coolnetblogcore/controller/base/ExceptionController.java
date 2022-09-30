package top.reminisce.coolnetblogcore.controller.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.pojo.vo.Result;

/**
 * @author BlueSkyCarry
 */
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result handler(Throwable ex){
        return ResultPack.error(ex.getMessage(), ex);
    }
}

