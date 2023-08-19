package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.model.Skill;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SkillRepository;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	SkillRepository skillRepository;

	
	@GetMapping("/employee")
	public ResponseEntity<Object> getEmployee() {
		List<Employee> employees = employeeRepository.findAll();
		try {
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping("/employee/{id}")
	public ResponseEntity<Object> getEmployee(@PathVariable Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		try {
			if (employee.isPresent()) {
				return new ResponseEntity<>(employee.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Employee not found!", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee body) {
		try {
			System.out.print("hi");
			Optional<Role> role = roleRepository.findById(4);
			System.out.print("hi1");
			body.setRole(role.get());
			System.out.print("hi2");
			Employee employee = employeeRepository.save(body);
			System.out.print("hi3");
			for (Skill skill : body.getSkills()) {
				System.out.print("hi4");
				skill.setEmployee(employee);
				skillRepository.save(skill);
			
			}
			System.out.print("hi5");
			return new ResponseEntity<>(employee, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Sever Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updatEmployee(@PathVariable Integer id,@RequestBody Employee body) {
		
		try {
			Optional<Employee> employee =employeeRepository.findById(id);
			if (employee.isPresent()) {
				employee.get().setFirstName(body.getFirstName());
				employee.get().setLastName(body.getLastName());
				employee.get().setSalary(body.getSalary());
				employeeRepository.save(employee.get());
				
				return new ResponseEntity<>(employee,HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Feiled!",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);		
			}
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
		try {
			Optional<Employee> employee =employeeRepository.findById(id);
			if (employee.isPresent()) {
				employeeRepository.delete(employee.get());
				return new ResponseEntity<>("Deleted!",HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Feiled!",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}