package com.learning.employee_service.controllers;

import com.learning.employee_service.io.EmployeeLoginRequest;
import com.learning.employee_service.io.JwtResponse;
import com.learning.employee_service.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Stack;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody EmployeeLoginRequest employeeLoginRequest) {
        return ResponseEntity.ok(authService.login(employeeLoginRequest));
    }

}
