package top.reminisce.coolnetblogcore.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;

import javax.servlet.http.HttpServletRequest;

import static top.reminisce.coolnetblogcore.common.CommonGlobalRef.EXCEPTION_IN_FILTER_FORWARD_ATTRIBUTE;

/**
 * @author BlueSky
 * @date 2022/10/14
 */
@RestController
@RequestMapping(value = "forward")
public class BaseForwardController {
    /**
     * 用于接受的错误异常在Filter层抛出的处理器Action
     * <br/>采用@RequestMapping，不指定method，接受任何方式请求
     * @param request 当前传递的请求
     * @throws Throwable
     */
    @RequestMapping(value = "exception-in-filter")
    public String exceptionInFilter(@NotNull HttpServletRequest request) throws Throwable {
        throw (Throwable) request.getAttribute(EXCEPTION_IN_FILTER_FORWARD_ATTRIBUTE);
    }

}
