package com.learning.employee_service.controllers;

import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.io.EmployeeAdminPatchDto;
import com.learning.employee_service.io.EmployeeCreateDto;
import com.learning.employee_service.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/employees")
@RequiredArgsConstructor
public class EmployeeAdminController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailsDto> getEmployee(@PathVariable Long employeeId){
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDetailsDto>> getEmployees(){
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDetailsDto> createEmployee(@Valid @RequestBody EmployeeCreateDto employeeCreationDetails){
        return new ResponseEntity<>(employeeService.createEmployee(employeeCreationDetails), HttpStatus.CREATED);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailsDto> updateEmployee(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeAdminPatchDto employeeAdminPatchDto)
    {
        return ResponseEntity.ok(employeeService.updateEmployeeByAdmin(employeeId, employeeAdminPatchDto));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}