package io.github.tuanpq.javafileio.common.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import io.github.tuanpq.javafileio.excel.service.ExcelFileService;
import io.github.tuanpq.javafileio.pdf.service.PDFFileService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	private final Path root = Paths.get("./uploads");

	@Autowired
	private PDFFileService pdfFileService;
	
	@Autowired
	private ExcelFileService excelFileService;
	
	@Override
	public void init() {
		try {
			Files.createDirectories(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	private void process(Path filePath) {
		try {
			String text = pdfFileService.readFile(filePath);
			excelFileService.writeFile(filePath, getKanjiSet(text));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(MultipartFile file) {
		try {
			Path filePath = this.root.resolve(file.getOriginalFilename());
			Files.copy(file.getInputStream(), filePath);
			process(filePath);
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			if (e instanceof FileAlreadyExistsException) {
				throw new RuntimeException("A file of that name already exists.");
			}

			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public boolean delete(String filename) {
		try {
			Path file = root.resolve(filename);
			return Files.deleteIfExists(file);
		} catch (IOException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch (IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

	@Override
	public Path getRootPath() {
		return root;
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
