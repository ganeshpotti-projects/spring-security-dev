package com.learning.employee_service.dto;

import com.learning.employee_service.entities.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailsDto {
    private Long id;

    private String title;

    private String description;

    private Employee employee;

    private LocalDateTime postedAt;

    private LocalDateTime updatedAt;
}
