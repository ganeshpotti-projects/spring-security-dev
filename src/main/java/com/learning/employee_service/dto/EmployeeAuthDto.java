package com.learning.employee_service.dto;

import com.learning.employee_service.enums.EmployeeStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeAuthDto {
    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Integer age;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private EmployeeStatus status;
}
