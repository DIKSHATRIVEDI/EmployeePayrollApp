package com.example.employeepayrollapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class EmployeePayrollAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EmployeePayrollAppApplication.class, args);
		String activeProfile = context.getEnvironment().getActiveProfiles().length > 0
				? context.getEnvironment().getActiveProfiles()[0]
				: "default";
		log.info("Employee Payroll App Started in {} environment", activeProfile);
	}
}

