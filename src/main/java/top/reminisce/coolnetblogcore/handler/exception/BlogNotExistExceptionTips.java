package top.reminisce.coolnetblogcore.handler.exception;

/**
 * @author BlueSky
 * @date 2022/10/8
 */
public class BlogNotExistExceptionTips extends BlogException{
    public BlogNotExistExceptionTips(String message) {
        super(message);
    }

    public BlogNotExistExceptionTips(String message, Throwable cause) {
        super(message, cause);
    }
}
