package com.bezkoder.spring.oracle;

import com.bezkoder.spring.oracle.model.Department;
import com.bezkoder.spring.oracle.model.Employee;
import com.bezkoder.spring.oracle.repository.DepartmentRepository;
import com.bezkoder.spring.oracle.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

@SpringBootTest
class SpringBootOracleApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Test
	void contextLoads() {
		Department department = departmentRepository.getOne(10);
		Collection<Employee> departments = department.getEmployees();
		System.out.println(departments.size());
	}
}
