package com.myrealestate.realestate;

import org.springframework.boot.SpringApplication;

public class TestRealEstateAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(RealEstateAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
