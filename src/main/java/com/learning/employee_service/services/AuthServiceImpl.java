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

    private final EmployeeService employeeService;

    private final SessionService sessionService;

    public JwtResponse login(EmployeeLoginRequest employeeLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(employeeLoginRequest.getEmail(), employeeLoginRequest.getPassword())
        );
        Employee employee = (Employee) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(employee);
        String refreshToken = jwtService.generateAccessToken(employee);

        sessionService.generateNewSession(employee, refreshToken);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .type("Bearer")
                .id(employee.getId())
                .email(employee.getEmail())
                .build();
    }

    @Override
    public JwtResponse refreshToken(String refreshToken) {
        Long employeeId = jwtService.getEmployeeIdFromToken(refreshToken);
        sessionService.validSession(refreshToken);
        Employee employee = employeeService.getEmployeeById(employeeId);
        String accessToken = jwtService.generateAccessToken(employee);
        return JwtResponse.builder()
                .id(employeeId)
                .email(employee.getEmail())
                .type("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
