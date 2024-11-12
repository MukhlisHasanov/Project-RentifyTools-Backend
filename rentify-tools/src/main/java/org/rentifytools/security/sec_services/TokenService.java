package org.rentifytools.security.sec_services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.rentifytools.entity.Role;
import org.rentifytools.security.AuthInfo;
import org.rentifytools.security.CustomUserDetails;
import org.rentifytools.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 6d5SM/khw/d4mEb4zRTrn0KVwz79LmUKOg0R1v/0lVI=
// DL96n5CqGIKlxg9LEH/GsyCzwoTlEmYBBbBHoKRCtmk=
@Service
public class TokenService {
    private SecretKey accessKey;
    private SecretKey refreshKey;
    private RoleService roleService;

    @Value("${access.token.days}")
    private int accessTokenDays;
    @Value("${refresh.token.days}")
    private int refreshTokenDays;

    public TokenService(@Value("${key.access}") String accessSecret,
                        @Value("${key.refresh}") String refreshSecret,
                        @Autowired RoleService roleService) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret));
        this.roleService = roleService;
    }

    public String generateAccessToken(CustomUserDetails userDetails) {
        Date expirationDate = calcExpirationDate(accessTokenDays);
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .expiration(expirationDate)
                .claim("roles", userDetails.getAuthorities())
                .claim("email", userDetails.getUsername())
                .signWith(accessKey)
                .compact();
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        Date expirationDate = calcExpirationDate(refreshTokenDays);
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    private Date calcExpirationDate(int days) {
        LocalDateTime currentTime = LocalDateTime.now();
        Instant instant = currentTime.plusDays(days)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        return Date.from(instant);
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessKey);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshKey);
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getAccessClaims(String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        return getClaims(refreshToken, refreshKey);
    }

    private Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public AuthInfo mapClaimsToAuthInfo(Claims claims) {
        String username = claims.getSubject();

        List<String> rolesList = (List<String>) claims.get("roles");

        Set<Role> roles = new HashSet<>();

        if (rolesList != null) {
            for (String roleName : rolesList) {
                String roleTitle = roleName.replace("ROLE_", "");
                Role role = roleService.getRole(roleTitle);
                if (role != null) {
                    roles.add(role);
                }
            }
        }
        return new AuthInfo(username, roles);
    }

}
