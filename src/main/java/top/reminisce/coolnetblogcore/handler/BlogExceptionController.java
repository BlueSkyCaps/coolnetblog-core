package top.reminisce.coolnetblogcore.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.common.ResultStatus;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.BlogExceptionService;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

/**
 * @author BlueSkyCarry
 */
@ControllerAdvice
public class BlogExceptionController {
    @Autowired
    private BlogExceptionService exceptionService;

    /**
     * 捕获全局异常。最低级
     * @param throwable 异常来源
     * @return Result数据体
     */
    @Order(LOWEST_PRECEDENCE)
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result handler(Throwable throwable){
        ResultStatus status = exceptionService.determineExceptionKinds(throwable);
        if (status == ResultStatus.OTHER) {
            return ResultPack.reject(throwable.getMessage());
        }
        return ResultPack.error(throwable.getMessage());
    }


    /**
     * 捕获BlogAccountNotRightExceptionTips异常，通常是身份验证失败。最高级
     * @param throwable 异常来源
     * @return Result数据体。且返回的是401状态码
     */
    @Order(0)
    @ExceptionHandler(value = {BlogAccountNotRightExceptionTips.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handlerAuthentication(Throwable throwable){
        ResultStatus status = exceptionService.determineExceptionKinds(throwable);
        if (status == ResultStatus.OTHER) {
            return ResultPack.reject(throwable.getMessage());
        }
        return ResultPack.error(throwable.getMessage());
    }

}

