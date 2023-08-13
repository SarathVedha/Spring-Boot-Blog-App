package com.vedha.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "SpringBootBlogApp",
				description = "SpringBootBlogApp REST APIs Docs",
				version = "v1.0",
				contact = @Contact(name = "Vedha", email = "sarath1242000@gmail.com"),
				license = @License(name = "@VedhaGroups")
		),
		servers = @Server(url = "/", description = "SpringBootBlogApp Runs")
)
public class SpringBootBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogAppApplication.class, args);
	}

}
