package com.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	List<Employee> findByNameLike(String word);
	List<Employee> findByAgeBetween(int fromAge, int toAge);
	List<Employee> findByUserid(String userid);
}
