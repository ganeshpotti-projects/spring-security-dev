package com.learning.employee_service.services;

import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.io.EmployeeAdminPatchDto;
import com.learning.employee_service.io.EmployeeCreateDto;
import com.learning.employee_service.io.EmployeeUpdateDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDetailsDto getEmployee(Long employeeId);

    List<EmployeeDetailsDto> getEmployees();

    EmployeeDetailsDto createEmployee(EmployeeCreateDto creationDetails);

    EmployeeDetailsDto updateEmployee(Long employeeId, EmployeeUpdateDto updatedDetails);

    void deleteEmployee(Long employeeId);

    EmployeeDetailsDto updateEmployeeByAdmin(Long employeeId, EmployeeAdminPatchDto employeeAdminPatchDto);

    Employee getEmployeeById(Long employeeId);

    Employee findByUsername(String username);
}
