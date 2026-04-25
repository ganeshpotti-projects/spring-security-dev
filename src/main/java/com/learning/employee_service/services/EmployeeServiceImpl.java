package com.learning.employee_service.services;

import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.enums.EmployeeStatus;
import com.learning.employee_service.dao.EmployeeDao;
import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.exceptions.ResourceNotFoundException;
import com.learning.employee_service.io.EmployeeAdminPatchDto;
import com.learning.employee_service.io.EmployeeCreateDto;
import com.learning.employee_service.io.EmployeeUpdateDto;
import com.learning.employee_service.mappers.EmployeeMapper;
import com.learning.employee_service.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

    private final EmployeeDao employeeDao;

    private final EmployeeMapper employeeMapper;

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeDetailsDto getEmployee(Long employeeId) {
        return employeeDao.findEmployeeById(employeeId);
    }

    @Override
    public List<EmployeeDetailsDto> getEmployees() {
        return employeeDao.findAllEmployees();
    }

    @Override
    public EmployeeDetailsDto createEmployee(EmployeeCreateDto creationDetails) {
        try{
            if(employeeDao.employeeExistsByEmailOrPhoneNumber(creationDetails.getEmail(), creationDetails.getPhoneNumber()))
                throw new ResourceNotFoundException("Employee Found with same Email or Phone Number");

            EmployeeDetailsDto employeeDetailsDto = employeeMapper.mapCreateDtoToDetailsDto(creationDetails);
            employeeDetailsDto.setRoles(creationDetails.getRoles());

            // Encode password & save into db
            String encodedPassword = passwordEncoder.encode(employeeDetailsDto.getPassword());
            employeeDetailsDto.setPassword(encodedPassword);

            employeeDetailsDto.setStatus(EmployeeStatus.ACTIVE);
            return employeeDao.createOrUpdateEmployee(employeeDetailsDto);
        } catch (ResponseStatusException ex){
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDetailsDto updateEmployee(Long employeeId, EmployeeUpdateDto updatedDetails) {
        EmployeeDetailsDto employeeDetailsDto = getEmployee(employeeId);
        employeeDetailsDto.setAge(updatedDetails.getAge());
        return employeeDao.createOrUpdateEmployee(employeeDetailsDto);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeDao.deleteEmployeeById(employeeId);
    }

    @Override
    public EmployeeDetailsDto updateEmployeeByAdmin(Long employeeId, EmployeeAdminPatchDto employeeAdminPatchDto) {
        EmployeeDetailsDto employeeDetailsDto = getEmployee(employeeId);
        employeeMapper.mapPatchDtoToDetailsDto(employeeAdminPatchDto, employeeDetailsDto);
        return employeeDao.createOrUpdateEmployee(employeeDetailsDto);
    }

    @Override
    public Employee loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username).orElseThrow(
                () -> new BadCredentialsException("User Not found with Email "+username)
        );
    }

    @Override
    public Employee findByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username).orElse(null);
    }

    @Override
    public Employee getEmployeeById(Long employeeId){
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with Id: "+employeeId));
    }
}
