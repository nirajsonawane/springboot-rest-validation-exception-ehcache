package com.niraj;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Configurable
@EnableCaching
public class Application {

	public static void main(String... args) {

		SpringApplication.run(Application.class, args);

	}

}
