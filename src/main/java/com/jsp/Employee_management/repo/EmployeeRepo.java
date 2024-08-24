package com.jsp.Employee_management.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.Employee_management.Entity.EmployeeDetails;
import com.jsp.Employee_management.clone.EmployeeClone;


public interface EmployeeRepo extends JpaRepository<EmployeeDetails, Integer> {
	@Query("delete from EmployeeDetails where email=?1")
	EmployeeDetails deleteByEmail(String email);

	Optional<EmployeeDetails> findByEmail(String email);
	
	@Query("select a from EmployeeDetails a where pwd=?1")
	EmployeeDetails fetchByPwd(String pwd);

	
	@Query("select a from EmployeeDetails a where pwd=?1 and email=?2")
	EmployeeDetails findByPwdEmail(String email, String pwd);

	
}
