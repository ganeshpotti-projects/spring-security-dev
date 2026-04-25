package com.learning.employee_service.dao;

import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.exceptions.ResourceNotFoundException;
import com.learning.employee_service.mappers.EmployeeMapper;
import com.learning.employee_service.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao{

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDetailsDto findEmployeeById(Long employeeId) {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with Id: "+employeeId));
        return employeeMapper.mapEntityToDetailsDto(employee);
    }

    @Override
    public List<EmployeeDetailsDto> findAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList
                .stream()
                .map(employeeMapper::mapEntityToDetailsDto
                )
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDetailsDto createOrUpdateEmployee(EmployeeDetailsDto employeeDetails) {
        Employee employee = employeeRepository.save(employeeMapper.mapDetailsDtoToEntity(employeeDetails));
        return employeeMapper.mapEntityToDetailsDto(employee);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        if(!employeeExistsById(employeeId))
            throw new ResourceNotFoundException("Employee Not found with Id: "+employeeId);
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Boolean employeeExistsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    @Override
    public Boolean employeeExistsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public Boolean employeeExistsByEmailOrPhoneNumber(String email, String phoneNumber) {
        return employeeRepository.existsByEmailOrPhoneNumber(email, phoneNumber);
    }


}
