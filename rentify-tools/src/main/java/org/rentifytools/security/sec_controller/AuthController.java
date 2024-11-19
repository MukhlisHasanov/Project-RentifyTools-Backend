package org.rentifytools.security.sec_controller;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.userDto.UserLoginDto;
import org.rentifytools.entity.User;
import org.rentifytools.security.sec_dto.RefreshRequestDto;
import org.rentifytools.security.sec_dto.TokenResponseDto;
import org.rentifytools.security.sec_services.AuthService;
import org.rentifytools.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody UserLoginDto user) {
        try {
            return authService.login(user);
        } catch (AuthException e) {
            return new TokenResponseDto(null, null);
        }
    }

    @PostMapping("/refresh")
    public TokenResponseDto getNewAccessToken(@RequestBody RefreshRequestDto refreshRequestDto) {
        return authService.getNewAccessToken(refreshRequestDto.getRefreshToken());
    }

    @GetMapping("/profile")
    public User getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return  userService.getUserByEmail(email).get();
    }
}
