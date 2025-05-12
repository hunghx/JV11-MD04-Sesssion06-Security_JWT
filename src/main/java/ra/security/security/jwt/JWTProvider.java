package ra.security.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Slf4j
@Component
public class JWTProvider {
    @Value("${jwt.sercret-key}") // lấy giá trị của thuộc tính trong file properties
    private String secretKey;
    @Value("${jwt.expired}")
    private long expiration;
    // Tao token
    public String generateToken(String username) {
        Date today = new Date();
        return Jwts.builder().setSubject(username) // mã hóa username
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
//    public String generateToken(UserDetails userDetails) {
//        Date today = new Date();
//        return Jwts.builder().setSubject(userDetails.getUsername()) // mã hóa username
//                .setPayload(userDetails.getAuthorities().toString())
//                .setIssuedAt(today)
//                .setExpiration(new Date(today.getTime() + expiration))
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }
//    public String refreshToken(String username) {
//        Date today = new Date();
//        return Jwts.builder().setSubject(username) // mã hóa username
//                .setIssuedAt(today)
//                .setExpiration(new Date(today.getTime() + expiration*10))
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }

    // Xác thực token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT: message error expired:", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT: message error unsupported:", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT: message error not formated:", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT: message error signature not math:", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT: message claims empty or argument invalid: ", e.getMessage());
        }
        return false;
    }
    // Giai mã token
    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
