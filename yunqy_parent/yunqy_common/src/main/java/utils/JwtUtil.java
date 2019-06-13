package utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * JWT生产token和解析token的工具类
 */
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key;  //token的加密盐

    private long ttl;  //过期时间，1个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生产token，或者签发token
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public String createJWT(String id,String subject,String roles) {
        long nowMillis = System.currentTimeMillis();
        long exp = nowMillis + ttl;
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).
                setSubject(subject).
                setIssuedAt(new Date()).
                signWith(SignatureAlgorithm.HS256, key).
                claim("roles",roles);
        //配置文件里的过期时间大于0，才判断是否过期
        if (ttl > 0) {
            jwtBuilder.setExpiration(new Date(exp));
        }
        String token = jwtBuilder.compact();
        System.out.println(token);
        return  token;
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public Claims parseJWT(String token) {
        return Jwts.parser().
                setSigningKey(key).
                parseClaimsJws(token).getBody();
    }
}
