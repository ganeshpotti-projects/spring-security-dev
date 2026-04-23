package com.learning.employee_service.services;

import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.io.EmployeeLoginRequest;
import com.learning.employee_service.io.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtResponse login(EmployeeLoginRequest employeeLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(employeeLoginRequest.getEmail(), employeeLoginRequest.getPassword())
        );
        Employee employee = (Employee) authentication.getPrincipal();
        String token = jwtService.generateToken(employee);
        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(employee.getId())
                .email(employee.getEmail())
                .build();
    }
}
