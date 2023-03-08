package top.reminisce.coolnetblogcore.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.reminisce.coolnetblogcore.common.ResultPack;
import top.reminisce.coolnetblogcore.common.ResultStatus;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.pojo.vo.Result;
import top.reminisce.coolnetblogcore.service.BlogExceptionService;

import java.util.List;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

/**
 * @author BlueSkyCarry
 */
@ControllerAdvice
public class BlogExceptionHandler {
    @Autowired
    private BlogExceptionService exceptionService;

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
        return getResult(throwable);
    }


    /**
     * 捕获spring-boot-starter-validation处理的接口数据验证未通过的异常，<br/>
     *
     * @param throwable MethodArgumentNotValidException
     * @return Result数据体，错误原因被明确解释
     */
    @Order(1)
    @ExceptionHandler(value = {org.springframework.web.bind.MethodArgumentNotValidException.class})
    @ResponseBody
    public Result handlerPostNotValidException(Throwable throwable, BindingResult bindingResult){
        String errValidMsg = validationDistinguish(bindingResult);
        return getResult(new Throwable(errValidMsg, throwable));
    }


    /**
     * 捕获全局异常。最低级
     * @param throwable 异常来源
     * @return Result数据体
     */
    @Order(LOWEST_PRECEDENCE)
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result handler(Throwable throwable){
        return getResult(throwable);
    }

    @NotNull
    private Result getResult(Throwable throwable) {
        ResultStatus status = exceptionService.determineExceptionKinds(throwable);
        if (status == ResultStatus.OTHER) {
            return ResultPack.reject(throwable.getMessage(), null,throwable.getStackTrace());
        }
        return ResultPack.error(throwable.getMessage(), null, throwable.getStackTrace());
    }

    /**
     * validation处理的接口数据验证未通过，从BindingResult中获取出自定义错误信息。
     * @param bindingResult validation验证无效封装的结果
     * @return 连接到的错误信息字符串
     */
    private String validationDistinguish(BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error:errors) {
                sb.append(error.getDefaultMessage()).append(System.lineSeparator());
            }
            int index = sb.lastIndexOf(System.lineSeparator());
            sb.delete(index, sb.length());
            return sb.toString();
        }
        return "";
    }
}

