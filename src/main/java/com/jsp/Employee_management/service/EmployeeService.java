package com.jsp.Employee_management.service;

import java.io.IOException;

import org.apache.el.stream.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.Employee_management.Entity.EmployeeDetails;
import com.jsp.Employee_management.Entity.LoginRequest;
import com.jsp.Employee_management.clone.EmployeeClone;

import com.jsp.Employee_management.dao.EmployeeDao;
import com.jsp.Employee_management.exception.EmailNotFound;
import com.jsp.Employee_management.exception.IdNotFound;
import com.jsp.Employee_management.exception.PwdNotFound;
import com.jsp.Employee_management.util.ResponseStructure;

import jakarta.mail.Header;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmployeeService {
	@Autowired
	EmployeeDao dao;

	@Autowired
	JavaMailSender mailsender;

	@Autowired
	ModelMapper mapper;

//saving operation
	public EmployeeDetails SaveEmployee(EmployeeDetails e) {
		EmployeeDetails ea = dao.saveEmployee(e);
		try {
			sendHtmlEmail(e);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return ea;
	}

//sending email through spring boot
	public void sendHtmlEmail(EmployeeDetails emp) throws MessagingException {
		MimeMessage message = mailsender.createMimeMessage();

		message.setFrom(new InternetAddress("jkaveridvk123@gmail.com"));
		message.setRecipients(MimeMessage.RecipientType.TO, emp.getEmail());
		message.setSubject("Test email from Spring");

		String htmlContent = "<h1>Hi " + emp.getName() + ",</h1>" +

				"We have received your application and appreciate the time you took to apply. "
				+ "Our team will review your qualifications and get back to you if your profile matches our needs. "
				+ "Thank you once again for considering a career with us. We encourage you to visit our <a href=\"[Careers Page URL]\">careers page</a> for future opportunities.</p>";

		message.setContent(htmlContent, "text/html; charset=utf-8");

		mailsender.send(message);
	}

//fetching details through find by id
	public ResponseEntity<ResponseStructure<EmployeeDetails>> fetchEmployeeDetails(int id) {

		EmployeeDetails db = dao.findById(id);
		if (db != null) {
			ResponseStructure<EmployeeDetails> rs = new ResponseStructure<EmployeeDetails>();
			rs.setStateCode(HttpStatus.CREATED.value());
			System.out.println(dao.findById(id));
			rs.setData(dao.findById(id));
			rs.setMessage("id  found  Sucessfully..!");
			return new ResponseEntity<ResponseStructure<EmployeeDetails>>(rs, HttpStatus.CREATED);

		} else {
			throw new IdNotFound();
		}
	}

// deleting by id
	public ResponseEntity<ResponseStructure<EmployeeDetails>> delete(int id) {
		ResponseStructure<EmployeeDetails> rs = new ResponseStructure<EmployeeDetails>();
		rs.setStateCode(HttpStatus.CREATED.value());
		rs.setData(dao.deleteById(id));
		rs.setMessage("employee deleted  Sucessfully..!");
		return new ResponseEntity<ResponseStructure<EmployeeDetails>>(rs, HttpStatus.CREATED);
	}

// updating Employee details
	public ResponseEntity<ResponseStructure<EmployeeDetails>> updateEmployee(EmployeeDetails employeeDetails) {

		EmployeeDetails db = dao.updateEmployee(employeeDetails);
		if (db != null) {
			ResponseStructure<EmployeeDetails> rs = new ResponseStructure<EmployeeDetails>();
			rs.setStateCode(HttpStatus.CREATED.value());
			System.out.println(dao.updateEmployee(employeeDetails));
			rs.setData(dao.updateEmployee(employeeDetails));
			rs.setMessage("id  found  Sucessfully..!");
			return new ResponseEntity<ResponseStructure<EmployeeDetails>>(rs, HttpStatus.CREATED);

		} else {
			throw new IdNotFound();
		}
	}
//login operation

//	 public ResponseEntity<ResponseStructure<EmployeeDetails>> loginEmployee(String email, String password) {
//	        EmployeeDetails emp = dao.findByEmail(email);
//	        ResponseStructure<EmployeeDetails> responseStructure = new ResponseStructure<>();
//
//	        if (emp != null && emp.getPwd().equals(password)) {
//	            responseStructure.setStateCode(HttpStatus.OK.value());
//	            responseStructure.setMessage("Login Successful");
//	            responseStructure.setData(emp);
//	            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
//	        } else {
//	            responseStructure.setStateCode(HttpStatus.UNAUTHORIZED.value());
//	            responseStructure.setMessage("Invalid Credentials");
//	            responseStructure.setData(null);
//	            return new ResponseEntity<>(responseStructure, HttpStatus.UNAUTHORIZED);
//	        }
//	    }
//login2222222222222222222

	public ResponseEntity<ResponseStructure<EmployeeDetails>> loginEmployee(LoginRequest login) {
		EmployeeDetails e = dao.findByEmail(login.getEmail());
		if (e != null) {
			EmployeeDetails empp = dao.empFetchByPwd(login.getPwd());
			if (login.getPwd().equals(e.getPwd())) {
				ResponseStructure<EmployeeDetails> rs = new ResponseStructure<EmployeeDetails>();
				rs.setStateCode(HttpStatus.CONTINUE.value());
				rs.setMessage("Login successfully.................!");
				rs.setData(mapper.map(e, EmployeeDetails.class));
				rs.setStateCode(HttpStatus.FOUND.value());
				return new ResponseEntity<ResponseStructure<EmployeeDetails>>(rs, HttpStatus.FOUND);
			} else {
				throw new IdNotFound("password is not found exception");
			}
		} else {
			throw new IdNotFound("email not found");
		}

	}

	// saving image operation

	public ResponseEntity<ResponseStructure<EmployeeClone>> saveImageById(int id, MultipartFile file)
			throws IOException {

		EmployeeDetails db = dao.findById(id);
		if (db != null) {
			db.setImage(file.getBytes());
			ResponseStructure<EmployeeClone> emp = new ResponseStructure<EmployeeClone>();

			emp.setStateCode(HttpStatus.ACCEPTED.value());
			emp.setMessage("image save sucessfully...!");
			emp.setData(mapper.map(dao.updateEmployee(db), EmployeeClone.class));
			return new ResponseEntity<ResponseStructure<EmployeeClone>>(HttpStatus.CREATED);
		} else {
			throw new IdNotFound("id is not found");
		}

	}

	// fetching image operation
	public ResponseEntity<byte[]> fetchImage(int id) {
		EmployeeDetails emp = dao.findById(id);
		if (emp != null) {
			byte[] data = dao.findById(id).getImage();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);

		} else
			throw new IdNotFound("id is not found exception");
	}

	public ResponseEntity<ResponseStructure<EmployeeDetails>> fetchPedemail(String email, String pwd) {
	    // Fetch the employee details based on the email
	    EmployeeDetails employee = dao.findByEmail(email);
	    
	    if (employee != null) {
	        // Check if the employee's password is null
	        if (employee.getPwd() != null && employee.getPwd().equals(pwd)) {
	            ResponseStructure<EmployeeDetails> rs = new ResponseStructure<>();
	            rs.setData(mapper.map(employee, EmployeeDetails.class));
	            rs.setMessage("Password is correct");
	            rs.setStateCode(HttpStatus.FOUND.value());
	            return new ResponseEntity<>(rs, HttpStatus.FOUND);
	        } else {
	            // Password is either null or incorrect
	            throw new PwdNotFound("Password is incorrect or not found.");
	        }
	    } else {
	        // No employee found with the given email
	        throw new EmailNotFound("email is incorrect or not found");
	    }
	}

	
	
}
