package com.company.service;

import static com.company.specification.EmployeeSpecifications.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.company.domain.Employee;
import com.company.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> findAll() {
		return employeeRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	public Employee findByUserid(String userid) {
		return employeeRepository.findByUserid(userid);
	}

	public List<Employee> findEmployees(String name, int ageFrom, int ageTo, String sex) {
		return employeeRepository.findAll(Specification
				.where(nameContains(name))
				.and(ageBetween(ageFrom, ageTo))
				.and(sexEqual(sex)), new Sort(Sort.Direction.ASC, "id"));
	}

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}
}
