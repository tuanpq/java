package io.github.tuanpq.javafileio.xml.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.github.tuanpq.javafileio.common.model.KanjiWord;
import io.github.tuanpq.javafileio.xml.service.XmlFileService;

@Controller
@RequestMapping(path = "/xml")
public class XmlFileController {
	
	@Autowired
	private XmlFileService xmlFileService;
	
	@GetMapping("/load")
	public String load(Model model) {
		xmlFileService.load();
		model.addAttribute("status", "Dictionary loading completed!");
		return "xml/load";
	}
	
	@GetMapping("/search/{kanji}")
	public String search(@PathVariable String kanji, Model model) {
		List<Document> dictionaryDocumentList = xmlFileService.getDictionaryDocumentList();
		List<KanjiWord> kanjiWordList = new ArrayList<KanjiWord>();
		dictionaryDocumentList.forEach(dd -> {
			Element element = dd.getDocumentElement();
			NodeList entryList = element.getElementsByTagName("entry");
			for (int i1 = 0; i1 < entryList.getLength(); i1++) {
				Node entryNode = entryList.item(i1);
				if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
					Element entryElement = (Element) entryNode;
					addWordContainsKanji(kanji,entryElement, kanjiWordList);
				}
			}
		});
		model.addAttribute("kanjiWordList", kanjiWordList);
		return "xml/search";
	}
	
	
	private void addWordContainsKanji(String kanji, Element entryElement, List<KanjiWord> kanjiWordList) {
		NodeList kebList = entryElement.getElementsByTagName("keb");
		
		if (kebList != null && kebList.getLength() > 0) {
			for (int i1 = 0; i1 < kebList.getLength(); i1++) {
				Node kebNode = kebList.item(i1);
				if (kebNode.getNodeType() == Node.ELEMENT_NODE) {
					Element kebElement = (Element) kebNode;
					if (kebElement.getTextContent().contains(kanji)) {
						KanjiWord kanjiWord = new KanjiWord();
						
						kanjiWord.setWord(kebElement.getTextContent());
						
						List<String> katakanaList = new ArrayList<String>();
						NodeList rebList = entryElement.getElementsByTagName("reb");
						if (rebList != null && rebList.getLength() > 0) {
							for (int i2 = 0; i2 < rebList.getLength(); i2++) {
								Node rebNode = rebList.item(i2);
								if (rebNode.getNodeType() == Node.ELEMENT_NODE) {
									Element rebElement = (Element) rebNode;
									katakanaList.add(rebElement.getTextContent());
								}
							}
						}
						kanjiWord.setKatakana(String.join(", ", katakanaList));
						
						List<String> meaningList = new ArrayList<String>();
						NodeList glossList = entryElement.getElementsByTagName("gloss");
						if (glossList != null && glossList.getLength() > 0) {
							for (int i3 = 0; i3 < glossList.getLength(); i3++) {
								Node glossNode = glossList.item(i3);
								if (glossNode.getNodeType() == Node.ELEMENT_NODE) {
									Element glossElement = (Element) glossNode;
									meaningList.add(glossElement.getTextContent());
								}
							}
						}
						kanjiWord.setGloss(String.join(", ", meaningList));
						
						kanjiWordList.add(kanjiWord);
						
						return;
					}
				}
			}
		}
	}

}
