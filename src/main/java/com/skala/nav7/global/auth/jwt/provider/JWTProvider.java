package com.skala.nav7.global.auth.jwt.provider;

import com.skala.nav7.global.auth.jwt.constant.AuthConstant;
import com.skala.nav7.global.auth.jwt.error.JWTErrorCode;
import com.skala.nav7.global.auth.jwt.error.JWTException;
import com.skala.nav7.global.auth.jwt.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JWTProvider {
    private final CustomUserDetailService userDetailService;
    private final SecretKey key;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JWTProvider(@Value("${Jwt.secret}") String secretKey,
                       @Value("${Jwt.token.access-expiration-time}") long accessExpiration,
                       @Value("${Jwt.token.refresh-expiration-time}") long refreshExpiration,
                       CustomUserDetailService userDetailService) {
        this.key = getSigningKey(secretKey);
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.userDetailService = userDetailService;
    }

    private SecretKey getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createAccessToken(Long id) {
        return createToken(id, this.accessExpiration);
    }

    public String createRefreshToken(Long id) {
        return createToken(id, this.refreshExpiration);
    }

    private String createToken(Long id, long expiration) {
        Instant issuedAt = Instant.now();
        Instant expiredAt = issuedAt.plusMillis(expiration);
        return Jwts.builder()
                .claim(AuthConstant.ROLE.getValue(), AuthConstant.ROLE_USER.getValue())
                .claim(AuthConstant.ID.getValue(), String.valueOf(id))
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiredAt))
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String id = getUserId(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(Cookie cookie) {
        String token = null;
        if (AuthConstant.ACCESS_TOKEN.getValue().equals(cookie.getName())) {
            token = cookie.getValue();
        }
        return token;
    }

    public Boolean isExpired(String token) {
        try {
            return getClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new JWTException(JWTErrorCode.TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            throw new JWTException(JWTErrorCode.TOKEN_EXPIRED);
        }
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public Long getExpiredIn(String token) {
        return getClaimsJws(token).getBody().getExpiration().getTime();
    }

    public String getRole(String token) {
        return getClaimsJws(token).getBody().get(AuthConstant.ROLE.getValue(), String.class);
    }

    public String getUserId(String token) {
        return String.valueOf(getClaimsJws(token).getBody().get(AuthConstant.ID.getValue(), String.class));
    }


}
