package com.company.specification;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.company.domain.Employee;

public class EmployeeSpecifications {

	public static Specification<Employee> nameContains(String name) {
		return StringUtils.isEmpty(name) ? null : new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("name"), "%" + name +"%");
			}
		};
	}

	public static Specification<Employee> ageBetween(int ageFrom, int ageTo) {
		return ageFrom == 0 || ageTo == 0 ? null : new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.between(root.get("age"), ageFrom, ageTo);
			}
		};
	}

	public static Specification<Employee> sexEqual(String sex) {
		return StringUtils.isEmpty(sex) ? null : new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("sex"), sex);
			}
		};
	}

}
