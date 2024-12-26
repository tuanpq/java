package io.github.tuanpq.javafileio.excel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.tuanpq.javafileio.common.model.KanjiList;
import io.github.tuanpq.javafileio.excel.service.ExcelFileService;

@Controller
@RequestMapping(path = "/excel")
public class ExcelFileController {

	@Autowired
	private ExcelFileService excelFileService;
	
	@GetMapping("/index")
	public String index(Model model) {
		KanjiList kanjiList = (KanjiList)model.getAttribute("kanjiList");
		if (kanjiList != null && kanjiList.getKanjiSet() != null) {
			excelFileService.writeFileToResources("KanjiList.xlsx", kanjiList.getKanjiSet());
		}
		return "excel/index";
	}
	
}
