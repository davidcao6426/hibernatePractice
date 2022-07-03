package com.bezkoder.spring.oracle;

import com.bezkoder.spring.oracle.model.Department;
import com.bezkoder.spring.oracle.model.Employee;
import com.bezkoder.spring.oracle.repository.DepartmentRepository;
import com.bezkoder.spring.oracle.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;

@SpringBootTest
class SpringBootOracleApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Test
	void test_get_employee_in_department() {
		Optional<Department> departmentData = departmentRepository.findById(10);
		Assert.isTrue(departmentData.isPresent());
		Department department = departmentData.get();
		Collection<Employee> departments = department.getEmployees();
		Assert.notEmpty(departments);
	}
}
