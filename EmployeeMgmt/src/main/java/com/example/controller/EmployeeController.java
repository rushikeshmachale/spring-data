package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Employee;
import com.example.repository.EmployeeRepo;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	
	@Autowired
	EmployeeRepo repo;
	
	@PostMapping("/post")
	public Employee add(@RequestBody Employee employee) {
		Employee emp = repo.save(employee);
		return emp;
	}

	@GetMapping("/get")
	public List<Employee> getAllEmployee() {
		List<Employee> list = repo.findAll();
		if(list.isEmpty()) {
			System.out.println("Database is empty");
		}
		
		return list;
	}
	
	@GetMapping("/find/{id}")
	public Employee findEmployee(@PathVariable int id){
		Optional<Employee> emp = repo.findById(id);
		Employee emp2=null;
		
		if(emp.isPresent()) {
			emp2= emp.get();
		}
		return emp2;
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable int id ) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
		}else {
			return "Employee not found";
		}
		return "Employee deleted..";
	}
	
	@PutMapping("/update/{id}")
	public String updateEmployee(@RequestBody Employee emp,@PathVariable int id) {
		if(repo.existsById(id)) {
			String nname=emp.getName();
			String ndept = emp.getDept();
			double nsalary = emp.getSalary();
			int nage = emp.getAge();
			Employee e1 = new Employee(id,nname,ndept,nsalary,nage);
			repo.save(e1);
		}else {
			return "Employee not found";
		}
		return "upadated..";
	}
	
	@PutMapping("/updatebyid/{id}")
	public String update(@PathVariable int id,@RequestBody Employee emp) {
		if(repo.existsById(id)) {
			Employee e= repo.findById(id).get();
			e.setDept(emp.getDept());
			repo.save(e);
		}else {
			return "Employee not found";
		}
		return "updated";
	}
//	
	@PostMapping("/login/{id}")
	public String login(@PathVariable int id,@RequestBody Employee emp) {
		String str= (repo.findById(id).get().getName());
		String userStr=emp.getName();
		System.out.println((String)(repo.findById(id).get().getName().toString())==(String)(emp.getName().toString()));
		
		if(repo.existsById(id)) {
//			if((String)(repo.findById(id).get().getName().toString())==(String)(emp.getName().toString())) {
			if(str==userStr) {
				return "Login success...";
			}else {
//				return "Invalid credentials...";
				return str +" "+userStr;
			}
		}else {
			return "Invalid id...";
		}
	}
}
