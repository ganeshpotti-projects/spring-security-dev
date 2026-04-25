package com.learning.employee_service.repositories;

import com.learning.employee_service.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}
