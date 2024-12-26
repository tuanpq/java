package io.github.tuanpq.javafileio;

import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.tuanpq.javafileio.common.service.FilesStorageService;
import io.github.tuanpq.javafileio.xml.service.XmlFileService;

@SpringBootApplication
public class JavaFileIOApplication implements CommandLineRunner {

	@Resource
	private FilesStorageService storageService;

	@Autowired
	private XmlFileService xmlFileService;

	public static void main(String[] args) {
		SpringApplication.run(JavaFileIOApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
		xmlFileService.load();
	}

}
