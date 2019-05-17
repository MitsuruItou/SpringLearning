package com.company.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.domain.Employee;
import com.company.domain.LoginForm;
import com.company.domain.SearchForm;
import com.company.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@MockBean
	EmployeeService service;

	@Autowired
	EmployeeController target;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@Test
	public void index() throws Exception {
		mockMvc.perform(get("/employees"))
			.andExpect(status().isOk())
			.andExpect(view().name("employees/login"));
	}

	@Test
	public void login_1() throws Exception {
		mockMvc.perform(get("/employees/login"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("errMessage", "指定したユーザは存在しません。"))
			.andExpect(view().name("employees/login"));
	}

	@Test
	public void login_2() throws Exception {

		LoginForm loginForm = new LoginForm();
		loginForm.setUserid("test");
		loginForm.setPass("test");

		Employee emp = new Employee();
		emp.setUserid("test");
		emp.setPass("test");
		emp.setName("テスト");

		when(service.findByUserid("test")).thenReturn(emp);

		mockMvc.perform(get("/employees/login").sessionAttr("loginForm", loginForm))
			.andExpect(status(). isFound())
			.andExpect(view().name("redirect:/employees/list"));

		assertThat(loginForm.getName()).isEqualTo("テスト");
		assertTrue(loginForm.isFlg());
	}

	@Test
	public void login_3() throws Exception {

		LoginForm loginForm = new LoginForm();
		loginForm.setUserid("test");
		loginForm.setPass("test");

		Employee emp = new Employee();
		emp.setUserid("test");
		emp.setPass("test1");
		emp.setName("テスト");

		when(service.findByUserid("test")).thenReturn(emp);

		mockMvc.perform(get("/employees/login").sessionAttr("loginForm", loginForm))
			.andExpect(status().isOk())
			.andExpect(model().attribute("errMessage", "パスワードに誤りがあります。"))
			.andExpect(view().name("employees/login"));
	}

	@Test
	public void logout() throws Exception {
		mockMvc.perform(get("/employees/logout"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/employees"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void list() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/employees/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("employees/search"))
			.andReturn();

		SearchForm searchForm = (SearchForm)mvcResult.getModelAndView().getModel().get("searchForm");
		List<Employee> list = (List<Employee>)mvcResult.getModelAndView().getModel().get("employees");

		assertNotNull(searchForm);
		assertNotNull(list);

		verify(service, times(1)).findAll();
	}

	@Test
	public void newEmployee() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/employees/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("employees/new"))
			.andReturn();

		Employee emp = (Employee)mvcResult.getModelAndView().getModel().get("employee");

		assertNotNull(emp);
	}

	@Test
	public void edit() throws Exception {

		long id = 1;

		when(service.findById(id)).thenReturn(Optional.of(new Employee()));

		MvcResult mvcResult = mockMvc.perform(get("/employees/{id}/edit", id))
			.andExpect(status().isOk())
			.andExpect(view().name("employees/edit"))
			.andReturn();

		Employee emp = (Employee)mvcResult.getModelAndView().getModel().get("employee");

		assertNotNull(emp);

		verify(service, times(1)).findById(id);
	}

	@Test
	public void show() throws Exception {

		long id = 1;

		when(service.findById(id)).thenReturn(Optional.of(new Employee()));

		MvcResult mvcResult = mockMvc.perform(get("/employees/{id}/show", id))
			.andExpect(status().isOk())
			.andExpect(view().name("employees/show"))
			.andReturn();

		Employee emp = (Employee)mvcResult.getModelAndView().getModel().get("employee");

		assertNotNull(emp);

		verify(service, times(1)).findById(id);
	}

	@Test
	public void create_1() throws Exception {

		mockMvc.perform(post("/employees/"))
			.andExpect(model().hasErrors())
			.andExpect(view().name("employees/new"));
	}

	@Test
	public void create_2() throws Exception {

		when(service.findByUserid("test")).thenReturn(new Employee());

		mockMvc.perform(post("/employees").param("id", "1").param("userid", "test")
				.param("pass", "test").param("name", "test").param("age", "20").param("sex", "male"))
			.andExpect(status(). isOk())
			.andExpect(model().attribute("errMsg", is("指定したユーザIDは既に存在します。")))
			.andExpect(view().name("employees/new"));
	}

	@Test
	public void create_3() throws Exception {

		mockMvc.perform(post("/employees").param("id", "1").param("userid", "test")
				.param("pass", "test").param("name", "test").param("age", "20").param("sex", "male"))
			.andExpect(status(). isFound())
			.andExpect(view().name("redirect:/employees/list"));

		verify(service, times(1)).save((Employee)any());
	}

	@Test
	public void update_1() throws Exception {

		long id = 1;

		mockMvc.perform(put("/employees/{id}/update", id))
			.andExpect(model().hasErrors())
			.andExpect(view().name("employees/edit"));
	}

	@Test
	public void update_2() throws Exception {

		long id = 1;

		mockMvc.perform(put("/employees/{id}/update", id).param("id", "1").param("userid", "test")
				.param("pass", "test").param("name", "test").param("age", "20").param("sex", "male"))
			.andExpect(status(). isFound())
			.andExpect(view().name("redirect:/employees/list"));

		verify(service, times(1)).save((Employee)any());
	}

	@Test
	public void destroy() throws Exception {

		long id = 1;

		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}/delete", id))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/employees/list"));

		verify(service, times(1)).delete(id);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void search() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/employees/search").param("name", "test").param("ageFrom", "10").param("ageTo", "20").param("sex", "male"))
			.andExpect(status().isOk())
			.andExpect(view().name("employees/search::list"))
			.andReturn();

		List<Employee> employees = (List<Employee>)mvcResult.getModelAndView().getModel().get("employees");

		assertNotNull(employees);

		verify(service, times(1)).findEmployees("test", 10, 20, "male");
	}

}
