package com.learning.employee_service.dto;

import com.learning.employee_service.enums.EmployeeRole;
import com.learning.employee_service.enums.EmployeeStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDetailsDto {
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Integer age;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private EmployeeStatus status;

    private String password;

    private Set<EmployeeRole> roles;
}
