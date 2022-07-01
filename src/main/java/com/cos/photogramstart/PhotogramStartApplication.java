package com.cos.photogramstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cos.photogramstart"})
public class PhotogramStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotogramStartApplication.class, args);
	}

}
