package com.jsp.Employee_management.controller;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.Employee_management.Entity.EmployeeDetails;
import com.jsp.Employee_management.Entity.LoginRequest;
import com.jsp.Employee_management.clone.EmployeeClone;
import com.jsp.Employee_management.service.EmployeeService;
import com.jsp.Employee_management.util.ResponseStructure;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.PUT })
@RestController
//@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	EmployeeService service;
	@Autowired
	ModelMapper mapper;

	@PostMapping("/save")
	public EmployeeClone register(@RequestBody EmployeeDetails e) {
		EmployeeDetails register = service.SaveEmployee(e);
		try {
			EmployeeClone ec = m1(register);
			return ec;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	@PostMapping("/emp")
	private EmployeeClone m1(@RequestBody EmployeeDetails emp) {
		EmployeeClone c = mapper.map(emp, EmployeeClone.class);
		return c;
	}

	@PostMapping("/sendhtml")
	public String sendHtml(@RequestBody EmployeeDetails emp) {
		try {
			service.sendHtmlEmail(emp);
			return "msg send successfully";
		} catch (Exception e) {
			return "internal error";
		}
	}

	@GetMapping("/find")
	public ResponseEntity<ResponseStructure<EmployeeDetails>> findById(@RequestParam int id) {
		return service.fetchEmployeeDetails(id);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseStructure<EmployeeDetails>> delete(@RequestParam int id) {
		return service.delete(id);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<EmployeeDetails>> update(@RequestBody EmployeeDetails employeeDetails) {
		return service.updateEmployee(employeeDetails);

	}

	@GetMapping("/login")
	public ResponseEntity<ResponseStructure<EmployeeDetails>> login(@RequestBody LoginRequest loginRequest) {
		return service.loginEmployee(loginRequest);
	}
	@GetMapping("/login2")
	public ResponseEntity<ResponseStructure<EmployeeDetails>> login(@RequestParam String email,@RequestParam String pwd){
		return service.fetchPedemail(email,pwd);
		
	} 

	@PostMapping("/image")
	public ResponseEntity<ResponseStructure<EmployeeClone>> saveImage(@RequestParam int id,
			@RequestParam MultipartFile file) throws IOException {
		return service.saveImageById(id, file);
	}

	@GetMapping("/fetchImage")
	public ResponseEntity<byte[]> findById1(@RequestParam int id) {
		return service.fetchImage(id);
	}
}
