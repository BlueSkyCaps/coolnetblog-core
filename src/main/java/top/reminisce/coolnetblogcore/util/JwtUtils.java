package top.reminisce.coolnetblogcore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author BlueSky
 * @date 2022/10/10
 */
public class JwtUtils {

    /**
     * 在当前服务器运行周期内可被使用的静态jwt密钥，用于生成和解析jwt。<br/>
     * 可以调用 genRandomSecretStringHs256 生成新密钥用于生成jwt，见genRandomSecretStringHs256()
     */
    public static String AVAILABLE_JWT_SECRET_KEY = genRandomSecretStringHs256();

    /**
     * 生成 HMAC-SHA 算法搭配的密钥，>= 256 位。<br/>
     * 使用 io.jsonwebtoken.security.keys#secretKeyFor（SignatureAlgorithm）来创建一个密钥，
     * 保证对于首选的 HMAC-SHA 算法来说足够安全。
     * @return 符合用于创建jwt的安全密钥 String
     */
    public static String genRandomSecretStringHs256(){
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Encoders.BASE64.encode(secretKey.getEncoded());
    }


    /**
     * 创建jwt，使用默认随机生成的密钥。
     * @return Map：生成的密钥secretString和token
     */
    public static Map<String, String> createToken(){
        String secretString = genRandomSecretStringHs256();
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder().signWith(key, SignatureAlgorithm.HS256).compact();
        return new HashMap<String, String>(2){{
            put("secretString", secretString);
            put("token", token);
        }};
    }

    /**
     * 创建jwt，指定密钥和主题
     * @param secretString 密钥
     * @param subject subject段
     * @return String token
     */
    public static String createToken(String secretString, String subject){
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setSubject(subject).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    /**
     * 创建jwt，指定过期时间，包含额外的claims
     * @param secretString 密钥
     * @param subject subject段
     * @param expiration 过期时间
     * @param claims 额为数据段
     * @return String token
     */
    public static String createToken(String secretString, String subject, Date expiration, Map<String, Object> claims){
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .setIssuer("god")
            .setSubject(subject)
            .setAudience("all over the world")
            .setExpiration(expiration)
            .setIssuedAt(new Date())
            .setId(UUID.randomUUID().toString())
            .addClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * 创建具有id标识的jwt，指定过期时间，包含额外的claims
     * @param secretString 密钥
     * @param subject subject段
     * @param id id标识
     * @param expiration 过期时间
     * @param claims 额为数据段
     * @return String token
     */
    public static String createToken(String secretString, String subject, String id, Date expiration,
                                     Map<String, Object> claims){
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .setIssuer("god")
            .setSubject(subject)
            .setAudience("all over the world")
            .setExpiration(expiration)
            .setIssuedAt(new Date())
            .setId(id)
            // note: do not use setClaims because it's can override original subject and so on
            .addClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * 创建有id标识的jwt，指定过期时间
     * @param secretString 密钥
     * @param subject subject段
     * @param id id标识
     * @param expiration 过期时间
     * @return String token
     */
    public static String createToken(String secretString, String subject, String id, Date expiration ){
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .setIssuer("god")
            .setAudience("all over the world")
            .setExpiration(expiration)
            .setIssuedAt(new Date())
            .setId(id)
            .setSubject(subject)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * 解析jwt，获取jwt完整对象
     * @param secretString 密钥
     * @param token jw token
     * @return 原生存储的jwt完整对象
     */
    public static Jws<Claims> parseToken(String secretString, String token){
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

    /**
     * 解析jwt，获取存储的Claims
     * @param secretString 密钥
     * @param token jw token
     * @return jwt中存储的Claims
     */
    public static Claims parseTokenGetClaimsBody(String secretString, String token){
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token).getBody();
    }
}
