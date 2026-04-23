package com.learning.employee_service.controllers;

import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.io.EmployeeUpdateDto;
import com.learning.employee_service.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailsDto> updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody EmployeeUpdateDto updatedDetails){
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, updatedDetails));
    }
}
