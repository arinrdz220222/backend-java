package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	@GetMapping("/employee")
	public List<Employee> getEmployee() {
		return employeeRepository.findAll();
	}
	@GetMapping("/employee/{id}")
	public Optional<Employee> getEmployee(@PathVariable Integer id) {
		Optional<Employee> employee =employeeRepository.findById(id);
		return employee;
	}

	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee body) {
		employeeRepository.save(body);
		return body;
	}
	
	@PutMapping("/employee/{id}")
	public Optional<Employee> updatEmployee(@PathVariable Integer id,@RequestBody Employee body) {
		Optional<Employee> employee =employeeRepository.findById(id);
		if (employee.isPresent()) {
			employee.get().setFirstName(body.getFirstName());
			employee.get().setLastName(body.getLastName());
			employee.get().setSalary(body.getSalary());
			employeeRepository.save(employee.get());
		}else {
			return null;
		}
		return employee;
	}
	
	@DeleteMapping("/employee/{id}")
	public String deleteEmployee(@PathVariable Integer id) {
		Optional<Employee> employee =employeeRepository.findById(id);
		if (employee.isPresent()) {
			employeeRepository.delete(employee.get());
			return "Deleted!";
		}else {
			return "Feiled!";
		}
	}

}