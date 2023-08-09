package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.jdbc.Expectations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRopository;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRopository employeeRopository;

	@GetMapping("/employee")
	public ResponseEntity<Object> getEmployee() {
		try {
			List<Employee> employees = employeeRopository.findAll();
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee body) {
		try {
			Employee employee = employeeRopository.save(body);
			return new ResponseEntity<>(employee, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<Object> getEmployeeDetail(@PathVariable Integer employeeId) {

		try {
			Optional<Employee> employee = employeeRopository.findById(employeeId);
			if (employee.isPresent()) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Employee Not Found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employee/{employeeId}")
	public ResponseEntity<Object> updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee body) {
		try {
			Optional<Employee> employee = employeeRopository.findById(employeeId);
			if (employee.isPresent()) {
				employee.get().setFirstName(body.getFirstName());
				employee.get().setFirstName(body.getLastName());
				employee.get().setSalary(body.getSalary());

				employeeRopository.save(employee.get());

				return new ResponseEntity<>(employee, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(employee, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/employee/{employeeId}")
	public ResponseEntity<Object> deleteEmplpoEmployee(@PathVariable Integer employeeId) {
		try {
			Optional<Employee> employee = employeeRopository.findById(employeeId);
			if (employee.isPresent()) {
				employeeRopository.delete(employee.get());

				return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Employee not found", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
