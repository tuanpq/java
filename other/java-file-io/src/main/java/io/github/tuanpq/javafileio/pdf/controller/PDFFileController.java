package io.github.tuanpq.javafileio.pdf.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.tuanpq.javafileio.common.model.KanjiList;
import io.github.tuanpq.javafileio.pdf.service.PDFFileService;

@Controller
@RequestMapping(path = "/pdf")
public class PDFFileController {
	
	@Autowired
	private PDFFileService pdfFileService;
	
	@GetMapping("/index")
	public String index(RedirectAttributes redirectAttributes) {
		try {
			String fileTextContent = pdfFileService.readFileFromResources("FullPathFile.pdf"); //TODO
			KanjiList kanjiList = new KanjiList();
			kanjiList.setKanjiSet(getKanjiSet(fileTextContent));
			redirectAttributes.addFlashAttribute("kanjiList", kanjiList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/excel/index";
	}
	
	private Set<String> getKanjiSet(String text) {
		Set<String> keys = new HashSet<>();
		for (int i = 0; i < text.length(); i++) {
			String c = String.valueOf(text.charAt(i));
			keys.add(c);
		}
		Set<String> hiraganaSet = new HashSet<String>();
		Set<String> katakanaSet = new HashSet<String>();
		Set<String> JapaneseStylePunctuation = new HashSet<String>();
		Set<String> fullWidthRomanAndHalfWidthKatakanaSet = new HashSet<String>();
		Set<String> cjkKanjiSet = new HashSet<String>();
		Set<String> cjkRareKanjiSet = new HashSet<String>();
		Set<String> other = new HashSet<String>();
		keys.forEach(key -> {
			int code = (int)key.charAt(0);
			if (code >= 0x3000 && code <= 0x303f) {
				JapaneseStylePunctuation.add(key);
			} else if (code >= 0x3040 && code <= 0x309f) {
				hiraganaSet.add(key);
			} else if (code >= 0x30a0 && code <= 0x30ff) {
				katakanaSet.add(key);
			} else if (code >= 0xff00 && code <= 0xffef) {
				fullWidthRomanAndHalfWidthKatakanaSet.add(key);
			} else if (code >= 0x4e00 && code <= 0x9faf) {
				cjkKanjiSet.add(key);
			} else if (code >= 0x3400 && code <= 0x4dbf) {
				cjkRareKanjiSet.add(key);
			} else {
				other.add(key);
			}
		});
		
		System.out.println("==========Japanese Style Punctuation==========");
		JapaneseStylePunctuation.forEach(key -> {
			int code = (int)key.charAt(0);
			System.out.println("" + key + "," + String.format("%04x", code));
		});
		System.out.println("==========Hiragana==========");
		hiraganaSet.forEach(key -> {
			int code = (int)key.charAt(0);
			System.out.println("" + key + "," + String.format("%04x", code));
		});
		System.out.println("==========Katakana==========");
		katakanaSet.forEach(key -> {
			int code = (int)key.charAt(0);
			System.out.println("" + key + "," + String.format("%04x", code));
		});
		System.out.println("==========Full-width Roman and Half-Width Katakana==========");
		fullWidthRomanAndHalfWidthKatakanaSet.forEach(key -> {
			int code = (int)key.charAt(0);
			System.out.println("" + key + "," + String.format("%04x", code));
		});
		System.out.println("==========CJK common and uncommon Kanji==========");
		cjkKanjiSet.forEach(key -> {
			int code = (int)key.charAt(0);
			System.out.println("" + key + "," + String.format("%04x", code));
		});
		System.out.println("==========Rare Kanji==========");
		cjkRareKanjiSet.forEach(key -> {
			int code = (int)key.charAt(0);
			System.out.println("" + key + "," + String.format("%04x", code));
		});
		System.out.println("==========Other==========");
		other.forEach(key -> {
			int code = (int)key.charAt(0);
			System.out.println("" + key + "," + String.format("%04x", code));
		});
		
		cjkKanjiSet.addAll(cjkRareKanjiSet);
		
		return cjkKanjiSet;
	}
	
}