package com.codeplay.codeplay_api;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class CodeplayApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodeplayApiApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
	}
}