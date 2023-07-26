package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	private List<Employee> data = new ArrayList<Employee>();

	@GetMapping("/employee")
	public List<Employee> getEmployee() {
		return employeeRopository.findAll();
	}

	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee body) {
		return employeeRopository.save(body);
	}

	@GetMapping("/employee/{employeeId}")
	public Optional<Employee> getEmployeeDetail(@PathVariable Integer employeeId) {

		Optional<Employee> employee = employeeRopository.findById(employeeId);

		return employee;
	}

	@PutMapping("/employee/{employeeId}")
	public Optional<Employee> updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee body) {
		Optional<Employee> employee = employeeRopository.findById(employeeId);
		if (employee.isPresent()) {
			employee.get().setFirstName(body.getFirstName());
			employee.get().setFirstName(body.getLastName());
			employee.get().setSalary(body.getSalary());

			employeeRopository.save(employee.get());

			return employee;
		} else {
			return null;
		}
	}

	@DeleteMapping("/employee/{employeeId}")
	public String deleteEmplpoEmployee(@PathVariable Integer employeeId) {
		Optional<Employee> employee = employeeRopository.findById(employeeId);
		if (employee.isPresent()) {
			employeeRopository.delete(employee.get());

			return "DELETE SUCCESS";
		} else {
			return "employee not found";
		}
	}
}
