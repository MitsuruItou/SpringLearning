package com.company.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.method.HandlerMethod;

import com.company.domain.Employee;
import com.company.domain.LoginForm;
import com.company.domain.SearchForm;
import com.company.service.EmployeeService;

@Controller
@EnableAutoConfiguration
@RequestMapping("employees")
@SessionAttributes(value = "loginForm")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute("loginForm")
	LoginForm loginForm() {
		return new LoginForm();
	}

	@GetMapping
	public String index(Model model) {
		return "employees/login";
	}

	@GetMapping("login")
	public String login(LoginForm form, Model model) throws Exception {
		throw new Exception();
//		List<Employee> employees = employeeService.findByUserid(form.getUserid());
//		if (employees.size() < 1) {
//			model.addAttribute("errMessage", "指定したユーザは存在しません。");
//			return "employees/login";
//		} else {
//			Employee emp = employees.get(0);
//			if (emp.getPass().equals(form.getPass())) {
//				form.setName(emp.getName());
//				form.setFlg(true);
//				return "redirect:/employees/list";
//			} else {
//				model.addAttribute("errMessage","パスワードに誤りがあります。");
//				return "employees/login";
//			}
//		}
	}

	@GetMapping("logout")
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/employees";
	}

	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("searchForm", new SearchForm());
		List<Employee> employees = employeeService.findAll();
		model.addAttribute("employees", employees);
		return "employees/search";
	}

	@GetMapping("new")
	public String newEmployee(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee",employee);
		return "employees/new";
	}

	@GetMapping("{id}/edit")
	public String edit(@PathVariable Long id, Model model) {
		Optional<Employee> employee = employeeService.findById(id);
		model.addAttribute("employee", employee.get());
		return "employees/edit";
	}

	@GetMapping("{id}/show")
	public String show(@PathVariable Long id, Model model) {
		Optional<Employee> employee = employeeService.findById(id);
		model.addAttribute("employee", employee.get());
		return "employees/show";
	}

	@PostMapping
	public String create(@Valid @ModelAttribute Employee employee, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "employees/new";
		}
		List<Employee> list = employeeService.findByUserid(employee.getUserid());
		if (list.size() > 0) {
			model.addAttribute("errMsg", "指定したユーザIDは既に存在します。");
			return "employees/new";
		}
		employeeService.save(employee);
		return "redirect:/employees/list";
	}

	@PutMapping("{id}/update")
	public String update(@PathVariable Long id, @Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "employees/edit";
		}
		employee.setId(id);
		employeeService.save(employee);
		return "redirect:/employees/list";
	}

	@DeleteMapping("{id}/delete")
	public String destroy(@PathVariable Long id) {
		employeeService.delete(id);
		return "redirect:/employees/list";
	}

	@GetMapping("search")
	public String search(@ModelAttribute SearchForm form, Model model) {
		List<Employee> list = employeeService.findEmployees(form.getName(), form.getAgeFrom(), form.getAgeTo(), form.getSex());
		model.addAttribute("employees", list);
		return "employees/search::list";
	}

	@ExceptionHandler(Exception.class)
	public String allExceptionHandler(HandlerMethod handlerMethod) {
//		model.addAttribute("errMessage", "例外が発生しました。再度、一覧画面から操作を実行してください。");

		return "employees/error";
	}

}
