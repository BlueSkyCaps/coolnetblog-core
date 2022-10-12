package top.reminisce.coolnetblogcore.handler.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author BlueSky
 * @date 2022/10/8
 */
public class BlogAccountNotRightExceptionTips extends BlogException{
    public BlogAccountNotRightExceptionTips(String message) {
        super(message);
    }
    public BlogAccountNotRightExceptionTips(String message, Throwable cause) {
        super(message, cause);
    }
}
