package com.onboardingassignment.oa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OnboardingAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnboardingAssignmentApplication.class, args);
	}

}
