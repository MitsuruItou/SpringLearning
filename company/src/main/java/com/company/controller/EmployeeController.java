package com.company.controller;

import static com.company.constants.MsgConst.*;
import static com.company.constants.PathConst.*;
import static com.company.util.CommonUtils.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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

	@Autowired
	private MessageSource msg;

	@ModelAttribute("loginForm")
	LoginForm loginForm() {
		return new LoginForm();
	}

	@GetMapping
	public String index(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return EMP_LOGIN;
	}

	@GetMapping("login")
	public String login(@Valid LoginForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return EMP_LOGIN;
		}
		String userid = form.getUserid();
		Employee employees = employeeService.findByUserid(userid);
		if (employees == null) {
			model.addAttribute("errMsg",
					msg.getMessage(MSG_USER_NOT_EXISTS, new String[] { userid }, Locale.JAPAN));
			return EMP_LOGIN;
		} else {
			if (employees.getPass().equals(form.getPass())) {
				form.setName(employees.getName());
				form.setFlg(true);
				return EMP_LIST_REDIRECT;
			} else {
				model.addAttribute("errMsg", msg.getMessage(MSG_PASS_MISTAKEN, null, Locale.JAPAN));
				return EMP_LOGIN;
			}
		}
	}

	@GetMapping("logout")
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return EMP_REDIRECT;
	}

	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("searchForm", new SearchForm());
		List<Employee> employees = employeeService.findAll();
		model.addAttribute("employees", employees);
		model.addAttribute("ageCombo", getAgeValMap());
		model.addAttribute("sexCombo", getSexValMap());
		return EMP_SEARCH;
	}

	@GetMapping("new")
	public String newEmployee(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("ageCombo", getAgeValMap());
		model.addAttribute("sexRadio", getSexValMap());
		return EMP_NEW;
	}

	@GetMapping("{id}/edit")
	public String edit(@PathVariable Long id, Model model) {
		Optional<Employee> employee = employeeService.findById(id);
		model.addAttribute("employee", employee.get());
		model.addAttribute("ageCombo", getAgeValMap());
		model.addAttribute("sexRadio", getSexValMap());
		return EMP_EDIT;
	}

	@GetMapping("{id}/show")
	public String show(@PathVariable Long id, Model model) {
		Optional<Employee> employee = employeeService.findById(id);
		model.addAttribute("employee", employee.get());
		return EMP_SHOW;
	}

	@PostMapping
	public String create(@Valid @ModelAttribute Employee employee, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return EMP_NEW;
		}
		String userid = employee.getUserid();
		Employee emp = employeeService.findByUserid(userid);
		if (emp != null) {
			model.addAttribute("errMsg",
					msg.getMessage(MSG_USER_ALLREADY_EXISTS, new String[] { userid }, Locale.JAPAN));
			return EMP_NEW;
		}
		employeeService.save(employee);
		return EMP_LIST_REDIRECT;
	}

	@PutMapping("{id}/update")
	public String update(@PathVariable Long id, @Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return EMP_EDIT;
		}
		employee.setId(id);
		employeeService.save(employee);
		return EMP_LIST_REDIRECT;
	}

	@DeleteMapping("{id}/delete")
	public String destroy(@PathVariable Long id) {
		employeeService.delete(id);
		return EMP_LIST_REDIRECT;
	}

	@GetMapping("search")
	public String search(@ModelAttribute SearchForm form, Model model) {
		List<Employee> list = employeeService.findEmployees(form.getName(), form.getAgeFrom(), form.getAgeTo(),
				form.getSex());
		model.addAttribute("employees", list);
		return EMP_SEARCH + "::list";
	}

}
