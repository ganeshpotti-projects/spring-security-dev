package com.learning.employee_service.services;

import com.learning.employee_service.io.EmployeeLoginRequest;
import com.learning.employee_service.io.JwtResponse;

public interface AuthService {
    JwtResponse login(EmployeeLoginRequest employeeLoginRequest);
}
