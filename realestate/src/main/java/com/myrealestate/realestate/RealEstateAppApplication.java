package com.myrealestate.realestate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class RealEstateAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateAppApplication.class, args);
	}
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name" ,defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}
