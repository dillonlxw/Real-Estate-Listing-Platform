package com.myrealestate.realestate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RealEstateAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateAppApplication.class, args);
	}
}
