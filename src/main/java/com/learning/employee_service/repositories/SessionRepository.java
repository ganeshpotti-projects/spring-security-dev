package com.learning.employee_service.repositories;

import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByEmployee(Employee employee);
    Optional<Session> findByRefreshToken(String refreshToken);
}
