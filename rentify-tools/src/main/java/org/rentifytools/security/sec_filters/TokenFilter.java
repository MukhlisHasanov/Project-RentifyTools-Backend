package org.rentifytools.security.sec_filters;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.rentifytools.security.AuthInfo;
import org.rentifytools.security.sec_services.TokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends GenericFilterBean {
    private final TokenService tokenService;
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletResponse;
        String authorizationHeader = request.getHeader("Authorization");
        String token = (authorizationHeader != null && !authorizationHeader.startsWith("Bearer ")) ? authorizationHeader.substring(7) : null;

        if (token != null && tokenService.validateAccessToken(token)) {
            Claims claims = tokenService.getAccessClaims(token);
            AuthInfo authInfo = tokenService.mapClaimsToAuthInfo(claims);
            authInfo.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authInfo);
        }
        filterChain.doFilter(request, servletResponse);
    }
}
