package com.jsp.Employee_management.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.Employee_management.Entity.EmployeeDetails;
import com.jsp.Employee_management.clone.EmployeeClone;
import com.jsp.Employee_management.repo.EmployeeRepo;

@Repository
public class EmployeeDao {
	@Autowired
	EmployeeRepo repo;

	public EmployeeDetails saveEmployee(EmployeeDetails u) {
		// TODO Auto-generated method stub
		return repo.save(u);
	}

	public EmployeeDetails findById(int id) {
		Optional<EmployeeDetails> r = repo.findById(id);
		if (r.isPresent()) {
			return r.get();
		} else {
			return null;
		}

	}

	public EmployeeDetails deleteById(int id) {
		Optional<EmployeeDetails> u = repo.findById(id);
		repo.delete(u.get());
		return u.get();
	}
	
	public EmployeeDetails empFetchByPwd(String pwd) {
		return repo.fetchByPwd(pwd);
		
	}


	public EmployeeDetails updateEmployee(EmployeeDetails emp) {
		Optional<EmployeeDetails> db = repo.findById(emp.getId());
		if (db.isPresent()) {

			EmployeeDetails empy = db.get();
			repo.save(emp);
			if (emp.getName() == null) {
				emp.setName(empy.getName());
			} else if (emp.getMname() == null) {
				emp.setMname(empy.getMname());
			} else if (emp.getLname() == null) {
				emp.setLname(empy.getLname());
			} else if (emp.getAge() == 0) {
				emp.setAge(0);
			} else if (emp.getGender() == null) {
				emp.setGender(empy.getGender());
			} else if (emp.getPhone() == 0) {
				emp.setPhone(0);
			} else if (emp.getEmail() == null) {
				emp.setEmail(empy.getEmail());
			} else if (emp.getPwd() == null) {
				emp.setPwd(empy.getPwd());
			} else if (emp.getDob() == null) {
				emp.setDob(empy.getDob());
			}
			return repo.save(emp);
		} else {
			return null;

		}

	}

	
	
	

	public EmployeeDetails findByEmail(String email) {
		 Optional<EmployeeDetails> r = repo.findByEmail(email);
	        if (r.isPresent()) {
	            return r.get();
	        } else {
	            return null;
	        }
	}

	public EmployeeDetails empFetchByPwdEmail(String email,String pwd) {
		// TODO Auto-generated method stub
		return repo.findByPwdEmail(email,pwd);
	}	

	

	
}
