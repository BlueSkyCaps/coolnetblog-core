package top.reminisce.coolnetblogcore.handler.exception;

/**
 * @author BlueSky
 * @date 2022/10/8
 */
public class BlogLeaveLimitExceptionTips extends BlogException{
    public BlogLeaveLimitExceptionTips(String message) {
        super(message);
    }

    public BlogLeaveLimitExceptionTips(String message, Throwable cause) {
        super(message, cause);
    }
}
