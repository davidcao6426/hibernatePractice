package com.bezkoder.spring.oracle.controller;

import com.bezkoder.spring.oracle.model.Department;
import com.bezkoder.spring.oracle.model.Tutorial;
import com.bezkoder.spring.oracle.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DepartmentController {

	@Autowired
	DepartmentRepository departmentRepository;

	@GetMapping("/departments")
	public ResponseEntity<List<Department>> getAllDepartments(@RequestParam(required = false) String title) {
		try {
			List<Department> departments = new ArrayList<>();

			if (title == null)
				departments.addAll(departmentRepository.findAll());
//			else
//				departments.addAll(departmentRepository.findByTitleContaining(title));

			if (departments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(departments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/departments/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable("id") int id) {
		Optional<Department> departmentData = departmentRepository.findById(id);

		if (departmentData.isPresent()) {
			Department department = departmentData.get();
			return new ResponseEntity<>(department, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	@PostMapping("/departments")
//	public ResponseEntity<Tutorial> createTutorial(@RequestBody Department department) {
//		try {
//			Tutorial _tutorial = departmentRepository
//					.save(new Department(department.getDeptName(), department.getLocation(), false));
//			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@PutMapping("/departments/{id}")
	public ResponseEntity<Department> updateTutorial(@PathVariable("id") int id, @RequestBody Department department) {
		Optional<Department> departmentData = departmentRepository.findById(id);

		if (departmentData.isPresent()) {
			Department _department = departmentData.get();
			_department.setDeptName(department.getDeptName());
			_department.setLocation(department.getLocation());
			_department.setEmployees(department.getEmployees());
			return new ResponseEntity<>(departmentRepository.save(_department), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/departments/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") int id) {
		try {
			departmentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/departments")
	public ResponseEntity<HttpStatus> deleteAllDepartments() {
		try {
			departmentRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
