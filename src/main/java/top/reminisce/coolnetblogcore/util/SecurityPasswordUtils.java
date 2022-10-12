package top.reminisce.coolnetblogcore.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author BlueSky
 * @date 2022/10/10
 */
public class SecurityPasswordUtils {
    /**
     * 使用BCrypt-PasswordEncoder，验证明文密码和密文密码是否匹配
     * @param rawPassword 明文
     * @param result 密文
     * @return true，匹配；other，false
     */
    public static boolean passwordMatch(String rawPassword, String result){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, result);
    }

    /**
     * 生成密文，使用BCrypt。
     * @param rawPassword 明文密码
     * @return 密文
     */
    public static String genBcryptPassword(String rawPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }
}
