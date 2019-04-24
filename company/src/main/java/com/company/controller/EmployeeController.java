package com.company.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.company.domain.Employee;
import com.company.service.EmployeeService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public ModelAndView index(Model model) {
		List<Employee> employees = employeeService.findAll();
		model.addAttribute("employees", employees);
		return new ModelAndView("employees/index");
	}

	@GetMapping("new")
	public ModelAndView newEmployee(Model model) {

		Employee employee = new Employee();
		model.addAttribute("employee",employee);
		return new ModelAndView("employees/new");
	}

	@GetMapping("{id}/edit")
	public ModelAndView edit(@PathVariable Long id, Model model) {
		Optional<Employee> employee = employeeService.findById(id);
		model.addAttribute("employee", employee.get());
		return new ModelAndView("employees/edit");
	}

	@GetMapping("{id}")
	public ModelAndView show(@PathVariable Long id, Model model) {
		Optional<Employee> employee = employeeService.findById(id);
		model.addAttribute("employee", employee.get());
		return new ModelAndView("employees/show");
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
		return new ModelAndView("redirect:/employees");
	}

	@DeleteMapping("{id}")
	public ModelAndView destroy(@PathVariable Long id) {
		employeeService.delete(id);
		return new ModelAndView("redirect:/employees");
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String handleMethodArgumentNotValidException(
			MethodArgumentNotValidException error) {
		return error.getMessage();
	}

}
