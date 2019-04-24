package com.company;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // ①
public class HelloWorld {
	@GetMapping("/") // ②
	public String hello() {
		return "hello"; // ③
	}
}