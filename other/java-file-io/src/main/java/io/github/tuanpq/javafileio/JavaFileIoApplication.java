package io.github.tuanpq.javafileio;

import jakarta.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.tuanpq.javafileio.common.service.FilesStorageService;

@SpringBootApplication
public class JavaFileIoApplication implements CommandLineRunner {

	@Resource
	FilesStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(JavaFileIoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
	}

}
