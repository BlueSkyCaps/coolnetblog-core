package top.reminisce.coolnetblogcore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.common.ResultStatus;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.BlogExceptionService;

/**
 * @author BlueSkyCarry
 */
@ControllerAdvice
public class BlogExceptionController {
    @Autowired
    private BlogExceptionService exceptionService;

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result handler(Throwable throwable){
        ResultStatus status = exceptionService.determineExceptionKinds(throwable);
        if (status == ResultStatus.OTHER) {
            return ResultPack.reject(throwable.getMessage());
        }
        return ResultPack.error(throwable.getMessage());
    }

}

