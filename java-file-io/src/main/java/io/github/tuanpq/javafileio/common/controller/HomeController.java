package io.github.tuanpq.javafileio.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String homepage() {
		return "redirect:/files";
	}

}
