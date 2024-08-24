package com.jsp.Employee_management.Entity;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class EmployeeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String Mname;
	private String Lname;
	private int age;
	private String gender;
	@Column(unique =true )
	private long phone;
	@Column(unique =true )
	private String email;
	
	private String pwd;
	private String dob;
	@Lob
	@Column(columnDefinition = "LONGBLOB", length = 999999999)
	byte[] image;
	private @OneToMany(cascade = CascadeType.ALL) List<Experience> experience;
	private @OneToMany(cascade = CascadeType.ALL) List<EducationDetails> educationdetails;

}
