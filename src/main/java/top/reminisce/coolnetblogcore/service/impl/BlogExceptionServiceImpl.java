package top.reminisce.coolnetblogcore.service.impl;

import org.springframework.stereotype.Service;
import top.reminisce.coolnetblogcore.common.ResultStatus;
import top.reminisce.coolnetblogcore.handler.exception.BlogAccountNotRightExceptionTips;
import top.reminisce.coolnetblogcore.handler.exception.BlogException;
import top.reminisce.coolnetblogcore.handler.exception.BlogLeaveLimitExceptionTips;
import top.reminisce.coolnetblogcore.handler.exception.BlogNotExistExceptionTips;
import top.reminisce.coolnetblogcore.service.BlogExceptionService;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author BlueSky
 * @date 2022/10/8
 */
@Service
public class BlogExceptionServiceImpl implements BlogExceptionService {
    @Override
    public ResultStatus determineExceptionKinds(Throwable throwable) {
        if (REASONABLE_BUSINESS_BLOG_EXCEPTION_ARRAY_LIST.contains(throwable.getClass())){
            return ResultStatus.OTHER;
        }
        return ResultStatus.FAIL;
    }

    /**
     * 允许的博客业务异常定义列表，此类异常被ControllerAdvice-BlogExceptionController捕获到时，
     * 用于进行放行包含非错误状态码（ResultStatus.OTHER）的响应体。<br>
     * 如，抛出BlogLeaveLimitException，表示当日某ip的评论留言数超出了设置的限制数，此类异常是正常的响应，且不应被日志进行错误的记录。<br>
     * 如，抛出BlogNotExistExceptionTips，表示某条记录已不存在。<br>
     * 如，抛出BlogAccountNotRightExceptionTips，表示请求的接口需要身份验证，但失败。<br>
     * ......。
     */
    private static final ArrayList<Class<? extends BlogException>> REASONABLE_BUSINESS_BLOG_EXCEPTION_ARRAY_LIST = new ArrayList<>();
    static {
        REASONABLE_BUSINESS_BLOG_EXCEPTION_ARRAY_LIST.addAll(Arrays.asList(
            BlogLeaveLimitExceptionTips.class,
            BlogNotExistExceptionTips.class,
            BlogAccountNotRightExceptionTips.class
        ));
    }
}
