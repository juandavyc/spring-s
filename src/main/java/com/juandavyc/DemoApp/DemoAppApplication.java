package com.juandavyc.DemoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoAppApplication {

	public static void main(String[] args) {

		final var context =SpringApplication.run(DemoAppApplication.class, args);

		final var dev = context.getBean(Dev.class);

		dev.build();
	}

}
