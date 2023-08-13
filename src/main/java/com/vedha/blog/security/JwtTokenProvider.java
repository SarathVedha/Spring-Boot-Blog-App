package com.vedha.blog.security;

import com.vedha.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-day-milliseconds}")
    private String jwtExpireDate;

    /**
     * <a href="https://jwt.io/">...</a>
     * sample token = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
     * HEADER:ALGORITHM & TOKEN TYPE
     * {
     *   "alg": "HS384"
     * }
     * PAYLOAD:DATA
     * {
     *   "sub": "admin@gmail.com",
     *   "iat": 1691337076,
     *   "exp": 1691941876
     * }
     * VERIFY SIGNATURE
     * HMACSHA384(
     *   base64UrlEncode(header) + "." +
     *   base64UrlEncode(payload),your-256-bit-secret
     *   )
     *   Header.Payload.Signature = JWT Token
     */

    //Create Token
    public String createToken(Authentication authentication) {

        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + Long.parseLong(jwtExpireDate));

        // Adding userName in subject and issuedDate is current Date, expired Date is currentDate + jwtExpireDate, signInKey is secret
        return Jwts.builder().setSubject(userName).setIssuedAt(currentDate).setExpiration(expireDate).signWith(getKey(jwtSecret)).compact();

    }

    // Create Key by using secret
    public Key getKey(String jwtSecret) {

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Get UserName From JwtToken
    public String getUserNameInJwtToken(String jwtToken) {

        Claims claims = Jwts.parserBuilder().setSigningKey(getKey(jwtSecret)).build().parseClaimsJws(jwtToken).getBody();

        return claims.getSubject();
    }

    // Validate Jwt Tokens
    public boolean validateJwtToken(String token) {

        try {

            Jwts.parserBuilder().setSigningKey(getKey(jwtSecret)).build().parse(token);

            return true;

        }catch (MalformedJwtException malformedJwtException) {
            throw new BlogApiException("Invalid JWT Token");
        }catch (ExpiredJwtException expiredJwtException) {
            throw new BlogApiException("Jwt Token Expired");
        }catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BlogApiException("Unsupported Jwt Token");
        }catch (IllegalArgumentException illegalArgumentException) {
            throw new BlogApiException("Jwt Token Is Empty");
        }
    }

}
