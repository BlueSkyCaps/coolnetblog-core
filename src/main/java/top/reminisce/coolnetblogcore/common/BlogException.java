package top.reminisce.coolnetblogcore.common;

/**
 * @author BlueSky
 * @date 2022/10/2
 */
public class BlogException extends RuntimeException{
    public BlogException(String message) {
        super(message);
    }

    public BlogException(String message, Throwable cause) {
        super(message, cause);
    }

}
