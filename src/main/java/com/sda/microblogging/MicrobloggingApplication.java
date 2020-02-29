package com.sda.microblogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class MicrobloggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrobloggingApplication.class, args);
	} 

}
