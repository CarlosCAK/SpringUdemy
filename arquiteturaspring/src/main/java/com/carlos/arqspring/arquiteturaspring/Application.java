package com.carlos.arqspring.arquiteturaspring;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);

		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
		builder.lazyInitialization(true);

		builder.bannerMode(Banner.Mode.OFF);
		builder.run(args);

		ConfigurableApplicationContext context = builder.context();

		ExemploValue value = context.getBean(ExemploValue.class);
		value.imprimirVariavel();

	}



}
