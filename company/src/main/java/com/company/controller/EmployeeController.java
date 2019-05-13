package com.company.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.company.domain.Employee;
import com.company.domain.LoginForm;
import com.company.service.EmployeeService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/employees")
@SessionAttributes(value = "loginForm")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute("loginForm")
	LoginForm loginForm() {
		return new LoginForm();
	}

	@GetMapping
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("employees/login");
		return mav;
	}

	@PostMapping("login")
	public ModelAndView login(LoginForm form) {
		ModelAndView mav = new ModelAndView();
		List<Employee> employees = employeeService.findByUserid(form.getUserid());
		if (employees.size() < 1) {
			mav.addObject("errMessage", "指定したユーザは存在しません。");
			mav.setViewName("/employees/login");
		} else {
			Employee emp = employees.get(0);
			if (emp.getPass().equals(form.getPass())) {
				form.setName(emp.getName());
				form.setFlg(true);
				List<Employee> allEmployees = employeeService.findAll();
				mav.addObject("employees", allEmployees);
				mav.setViewName("/employees/list");
			} else {
				mav.addObject("errMessage","パスワードに誤りがあります。");
				mav.setViewName("/employees/login");
			}
		}

		return mav;
	}

	@GetMapping("logout")
	public ModelAndView logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return new ModelAndView("redirect:/employees");
	}

	@GetMapping("list")
	public ModelAndView list(ModelAndView mav) {
		List<Employee> employees = employeeService.findAll();
		mav.addObject("employees", employees);
		return mav;
	}

	@GetMapping("new")
	public ModelAndView newEmployee(ModelAndView mav) {

		Employee employee = new Employee();
		mav.addObject("employee",employee);
		mav.setViewName("employees/new");
		return mav;
	}

	@GetMapping("{id}/edit")
	public ModelAndView edit(@PathVariable Long id, ModelAndView mav) {
		Optional<Employee> employee = employeeService.findById(id);
		mav.addObject("employee", employee.get());
		mav.setViewName("employees/edit");
		return mav;
	}

	@GetMapping("{id}")
	public ModelAndView show(@PathVariable Long id, ModelAndView mav) {
		Optional<Employee> employee = employeeService.findById(id);
		mav.addObject("employee", employee.get());
		mav.setViewName("employees/show");
		return mav;
	}

	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Employee create(@RequestBody Employee employee) {
		employeeService.save(employee);
		return employee;
	}

	@PutMapping("{id}")
	public ModelAndView update(@PathVariable Long id, @Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("/employees/edit");
		}
		employee.setId(id);
		employeeService.save(employee);
		return new ModelAndView("redirect:/employees/list");
	}

	@DeleteMapping("{id}")
	public ModelAndView destroy(@PathVariable Long id) {
		employeeService.delete(id);
		return new ModelAndView("redirect:/employees/list");
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView allExceptionHandler() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/employees/error");
		mav.addObject("errMessage", "例外が発生しました。再度、一覧画面から操作を実行してください。");

		return mav;
	}

}
