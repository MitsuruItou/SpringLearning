package com.company.handler;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.company.controller.EmployeeController;
import com.company.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeExceptionHandlerTest {

	private MockMvc mockMvc;

	@MockBean
	EmployeeService service;

	@Autowired
	EmployeeController target;

	@Before
	public void setup() {
		final StaticApplicationContext applicationContext = new StaticApplicationContext();
		applicationContext.registerSingleton("exceptionHandler", EmployeeExceptionHandler.class);

		final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
		webMvcConfigurationSupport.setApplicationContext(applicationContext);

		mockMvc = MockMvcBuilders.standaloneSetup(target).setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver()).build();
	}

	@Test
	public void allExceptionHandler() throws Exception{

		when(service.findAll()).thenThrow(RuntimeException.class);

		mockMvc.perform(get("/employees/list"))
			.andExpect(status().isInternalServerError())
			.andExpect(model().attribute("errMessage", "例外が発生しました。再度、一覧画面から操作を実行してください。"))
			.andExpect(model().attribute("errDetail", "Exception:java.lang.RuntimeException"))
			.andExpect(view().name("employees/error"));
	}

}
