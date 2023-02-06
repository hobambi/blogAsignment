package com.hobambi.blogasignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlogAsignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAsignmentApplication.class, args);
	}

}
