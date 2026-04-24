package com.learning.employee_service.controllers;

import com.learning.employee_service.io.EmployeeLoginRequest;
import com.learning.employee_service.io.JwtResponse;
import com.learning.employee_service.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Stack;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody EmployeeLoginRequest employeeLoginRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        JwtResponse jwtResponse = authService.login(employeeLoginRequest);
        Cookie cookie = new Cookie("refreshToken", jwtResponse.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the cookies"));
        JwtResponse jwtResponse = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(jwtResponse);
    }
}
