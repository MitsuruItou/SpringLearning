package com.company.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.domain.Employee;
import com.company.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

	private final static long ID = 100;

	private final static String USER_ID = "test_user_id";

	private final static String PASS = "test_pass";

	private final static String NAME = "test_name";

	private final static int AGE = 20;

	private final static String SEX = "test_sex";

	private final static String ADDRESS = "test_address";

	private final static String TEL = "test_tell";

	private Employee employee;

	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeService employeeService;

	@Before
	public void setup() {
		this.employee = new Employee();
		this.employee.setId(ID);
		this.employee.setUserid(USER_ID);
		this.employee.setPass(PASS);
		this.employee.setName(NAME);
		this.employee.setAge(AGE);
		this.employee.setSex(SEX);
		this.employee.setAddress(ADDRESS);
		this.employee.setTel(TEL);
	}

	@Test
	public void findAll() {

		List<Employee> list = new ArrayList<>();

		Employee emp1 = makeTestEmployee(1, "test1", "test1", "test1", 20, "", "", "");
		Employee emp2 = makeTestEmployee(1, "test2", "test2", "test2", 20, "", "", "");
		Employee emp3 = makeTestEmployee(1, "test3", "test3", "test3", 20, "", "", "");

		list.add(emp1);
		list.add(emp2);
		list.add(emp3);

		when(this.employeeRepository.findAll(new Sort(Sort.Direction.ASC, "id"))).thenReturn(list);

		List<Employee> actual = this.employeeService.findAll();

		assertThat(actual).isEqualTo(list);

		verify(this.employeeRepository, times(1)).findAll(new Sort(Sort.Direction.ASC, "id"));
	}

	@Test
	public void findById() {

		when(this.employeeRepository.findById(ID)).thenReturn(Optional.of(this.employee));

		Employee actual = this.employeeService.findById(ID).get();

		assertThat(actual).isEqualTo(this.employee);

		verify(this.employeeRepository, times(1)).findById(ID);
	}

	@Test
	public void findByUserid() {

		List<Employee> list = new ArrayList<>();

		Employee emp1 = makeTestEmployee(1, "test1", "test1", "test1", 20, "", "", "");

		list.add(emp1);

		when(this.employeeRepository.findByUserid("test1")).thenReturn(list);

		List<Employee> actual = this.employeeService.findByUserid("test1");

		assertThat(actual).isEqualTo(list);

		verify(this.employeeRepository, times(1)).findByUserid("test1");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findEmployees() {

		List<Employee> list = new ArrayList<>();

		Employee emp1 = makeTestEmployee(1, "test1", "test1", "test1", 20, "", "", "");
		Employee emp2 = makeTestEmployee(1, "test2", "test2", "test2", 20, "", "", "");
		Employee emp3 = makeTestEmployee(1, "test3", "test3", "test3", 20, "", "", "");

		list.add(emp1);
		list.add(emp2);
		list.add(emp3);

		when(this.employeeRepository.findAll((Specification<Employee>)any(), (Sort)any())).thenReturn(list);

		List<Employee> actual = this.employeeService.findEmployees("test", 0, 0, "");

		assertThat(actual).isEqualTo(list);

		verify(this.employeeRepository, times(1)).findAll((Specification<Employee>)any(), (Sort)any());
	}

	@Test
	public void save() {

		when(this.employeeRepository.save(this.employee)).thenReturn(this.employee);

		Employee actual = this.employeeService.save(this.employee);

		assertThat(actual).isEqualTo(this.employee);

		verify(this.employeeRepository, times(1)).save(this.employee);
	}

	@Test
	public void delete() {

		this.employeeService.delete(ID);

		verify(this.employeeRepository, times(1)).deleteById(ID);
	}

	public Employee makeTestEmployee(long id, String userid, String pass, String name, int age, String sex, String address, String tel) {
		Employee emp = new Employee();
		emp.setId(ID);
		emp.setUserid(USER_ID);
		emp.setPass(PASS);
		emp.setName(NAME);
		emp.setAge(AGE);
		emp.setSex(SEX);
		emp.setAddress(ADDRESS);
		emp.setTel(TEL);
		return emp;
	}

}
