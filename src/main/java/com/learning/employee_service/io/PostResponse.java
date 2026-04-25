package com.learning.employee_service.io;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime postedAt;

    private LocalDateTime updatedAt;
}
