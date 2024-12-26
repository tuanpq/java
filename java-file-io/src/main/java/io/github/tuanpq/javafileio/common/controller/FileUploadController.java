package io.github.tuanpq.javafileio.common.controller;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.tuanpq.javafileio.common.model.FileInformation;
import io.github.tuanpq.javafileio.common.service.FilesStorageService;
import io.github.tuanpq.javafileio.excel.service.ExcelFileService;

@Controller
public class FileUploadController {

	@Autowired
	private FilesStorageService storageService;
	
	@Autowired
	private ExcelFileService excelFileService;

	@GetMapping("/files/select")
	public String newFile(Model model) {
		return "uploadFile";
	}

	@PostMapping("/files/upload")
	public String uploadFile(Model model, @RequestParam MultipartFile file) {
		String message = "";

		try {
			storageService.save(file);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			model.addAttribute("message", message);
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
			model.addAttribute("message", message);
		}

		return "uploadFile";
	}

	@GetMapping("/files")
	public String getListFiles(Model model) {
		List<FileInformation> fileInfos = storageService.loadAll().map(path -> {
			String fileName = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(FileUploadController.class, "getFile", path.getFileName().toString()).build()
					.toString();
			FileInformation fileInformation = new FileInformation();
			fileInformation.setName(fileName);
			fileInformation.setUrl(url);
			return fileInformation;
		}).collect(Collectors.toList());

		model.addAttribute("files", fileInfos);

		return "files";
	}

	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/files/delete/{filename:.+}")
	public String deleteFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
		try {
			boolean existed = storageService.delete(filename);

			if (existed) {
				redirectAttributes.addFlashAttribute("message", "Delete the file successfully: " + filename);
			} else {
				redirectAttributes.addFlashAttribute("message", "The file does not exist!");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message",
					"Could not delete the file: " + filename + ". Error: " + e.getMessage());
		}

		return "redirect:/files";
	}
	
	@GetMapping("/files/view/{filename:.+}")
	public String viewFile(@PathVariable String filename, Model model) {
		Path filePath = storageService.getRootPath().resolve(filename);
		List<String> kanjiCharacterList = excelFileService.getKanjiCharacterList(filePath);
		model.addAttribute("kanjiCharacterList", kanjiCharacterList);
		return "excel/view";
	}

}
