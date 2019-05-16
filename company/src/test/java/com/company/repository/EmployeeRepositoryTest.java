package com.company.repository;

import static com.company.specification.EmployeeSpecifications.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.domain.Employee;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Before
	public void setup() {

		Employee emp1 = new Employee();
		emp1.setUserid("test1");
		emp1.setPass("test1pass");
		emp1.setName("テスト1");
		emp1.setAge(20);
		emp1.setSex("男性");

		Employee emp2 = new Employee();
		emp2.setUserid("test2");
		emp2.setPass("test2pass");
		emp2.setName("テスト2");
		emp2.setAge(25);
		emp2.setSex("女性");

		Employee emp3 = new Employee();
		emp3.setUserid("test3");
		emp3.setPass("test3pass");
		emp3.setName("テスト3");
		emp3.setAge(30);
		emp3.setSex("男性");

		testEntityManager.persist(emp1);
		testEntityManager.persist(emp2);
		testEntityManager.persist(emp3);

	}

	@Test
	public void findAll() {

		List<Employee> list = employeeRepository.findAll(new Sort(Sort.Direction.ASC, "id"));

		assertThat(list.size()).isEqualTo(3);

		assertThat(list.get(0).getUserid()).isEqualTo("test1");
		assertThat(list.get(0).getPass()).isEqualTo("test1pass");
		assertThat(list.get(0).getName()).isEqualTo("テスト1");
		assertThat(list.get(0).getAge()).isEqualTo(20);
		assertThat(list.get(0).getSex()).isEqualTo("男性");

		assertThat(list.get(1).getUserid()).isEqualTo("test2");
		assertThat(list.get(1).getPass()).isEqualTo("test2pass");
		assertThat(list.get(1).getName()).isEqualTo("テスト2");
		assertThat(list.get(1).getAge()).isEqualTo(25);
		assertThat(list.get(1).getSex()).isEqualTo("女性");

		assertThat(list.get(2).getUserid()).isEqualTo("test3");
		assertThat(list.get(2).getPass()).isEqualTo("test3pass");
		assertThat(list.get(2).getName()).isEqualTo("テスト3");
		assertThat(list.get(2).getAge()).isEqualTo(30);
		assertThat(list.get(2).getSex()).isEqualTo("男性");


	}

	@Test
	public void findById() {

		long id = 9;

		Optional<Employee> retEmp = employeeRepository.findById(id);

		assertThat(retEmp.get().getUserid()).isEqualTo("test3");
		assertThat(retEmp.get().getPass()).isEqualTo("test3pass");
		assertThat(retEmp.get().getName()).isEqualTo("テスト3");
		assertThat(retEmp.get().getAge()).isEqualTo(30);
		assertThat(retEmp.get().getSex()).isEqualTo("男性");


	}

	@Test
	public void findByUserid() {

		Employee emp = employeeRepository.findByUserid("test1");

		assertThat(emp).isEqualTo(1);

		assertThat(emp.getUserid()).isEqualTo("test1");
		assertThat(emp.getPass()).isEqualTo("test1pass");
		assertThat(emp.getName()).isEqualTo("テスト1");
		assertThat(emp.getAge()).isEqualTo(20);
		assertThat(emp.getSex()).isEqualTo("男性");


	}

	@Test
	public void findEmployees() {

		List<Employee> list1 = employeeRepository.findAll(Specification
				.where(nameContains("テスト1"))
				.and(ageBetween(0, 30))
				.and(sexEqual("")), new Sort(Sort.Direction.ASC, "id"));

		assertThat(list1.size()).isEqualTo(1);

		assertThat(list1.get(0).getUserid()).isEqualTo("test1");
		assertThat(list1.get(0).getPass()).isEqualTo("test1pass");
		assertThat(list1.get(0).getName()).isEqualTo("テスト1");
		assertThat(list1.get(0).getAge()).isEqualTo(20);
		assertThat(list1.get(0).getSex()).isEqualTo("男性");

		List<Employee> list2 = employeeRepository.findAll(Specification
				.where(nameContains(""))
				.and(ageBetween(26, 31))
				.and(sexEqual("")), new Sort(Sort.Direction.ASC, "id"));

		assertThat(list2.size()).isEqualTo(1);

		assertThat(list2.get(0).getUserid()).isEqualTo("test3");
		assertThat(list2.get(0).getPass()).isEqualTo("test3pass");
		assertThat(list2.get(0).getName()).isEqualTo("テスト3");
		assertThat(list2.get(0).getAge()).isEqualTo(30);
		assertThat(list2.get(0).getSex()).isEqualTo("男性");

		List<Employee> list3 = employeeRepository.findAll(Specification
				.where(nameContains(""))
				.and(ageBetween(30, 0))
				.and(sexEqual("女性")), new Sort(Sort.Direction.ASC, "id"));

		assertThat(list3.size()).isEqualTo(1);

		assertThat(list3.get(0).getUserid()).isEqualTo("test2");
		assertThat(list3.get(0).getPass()).isEqualTo("test2pass");
		assertThat(list3.get(0).getName()).isEqualTo("テスト2");
		assertThat(list3.get(0).getAge()).isEqualTo(25);
		assertThat(list3.get(0).getSex()).isEqualTo("女性");


	}

}
