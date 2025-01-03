package io.github.tuanpq.javafileio.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/excel")
public class ExcelFileController {

	@GetMapping("/index")
	public String index() {
		return "excel/index";
	}
	
}
