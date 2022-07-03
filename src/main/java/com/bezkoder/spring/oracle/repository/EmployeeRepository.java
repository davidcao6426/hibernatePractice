package com.bezkoder.spring.oracle.repository;

import com.bezkoder.spring.oracle.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
