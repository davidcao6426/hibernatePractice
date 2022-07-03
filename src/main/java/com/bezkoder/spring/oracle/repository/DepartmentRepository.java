package com.bezkoder.spring.oracle.repository;

import com.bezkoder.spring.oracle.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
