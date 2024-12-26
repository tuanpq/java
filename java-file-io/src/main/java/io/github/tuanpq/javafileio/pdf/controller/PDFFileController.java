package io.github.tuanpq.javafileio.pdf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/pdf")
public class PDFFileController {
	
	@GetMapping("/index")
	public String index() {
		return "pdf/index";
	}

}