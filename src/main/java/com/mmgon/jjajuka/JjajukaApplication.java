package com.mmgon.jjajuka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class JjajukaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjajukaApplication.class, args);
	}

}
