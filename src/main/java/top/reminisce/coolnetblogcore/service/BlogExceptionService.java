package top.reminisce.coolnetblogcore.service;

import top.reminisce.coolnetblogcore.common.ResultStatus;

/**
 * @author BlueSky
 * @date 2022/10/8
 */
public interface BlogExceptionService {
     /**
      * 判断引发异常的来源，若是合理的业务逻辑异常，需要不同的数据响应体，因为Status不同。
      * @param throwable 发异常的来源
      * @return 状态码
      */
     ResultStatus determineExceptionKinds(Throwable throwable);
}
