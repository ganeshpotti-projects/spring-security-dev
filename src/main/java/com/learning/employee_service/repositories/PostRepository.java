package com.learning.employee_service.repositories;

import com.learning.employee_service.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
