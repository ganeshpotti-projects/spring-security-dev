package com.learning.employee_service.dao;

import com.learning.employee_service.dto.EmployeeDetailsDto;

import java.util.List;

public interface EmployeeDao {
    EmployeeDetailsDto findEmployeeById(Long employeeId);

    List<EmployeeDetailsDto> findAllEmployees();

    EmployeeDetailsDto createOrUpdateEmployee(EmployeeDetailsDto employeeDetails);

    void deleteEmployeeById(Long employeeId);

    Boolean employeeExistsById(Long employeeId);

    Boolean employeeExistsByEmail(String email);

    Boolean employeeExistsByEmailOrPhoneNumber(String email, String phoneNumber);
}
