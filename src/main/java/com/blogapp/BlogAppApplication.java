package com.blogapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blogapp.entities.Categeory;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	public PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);

	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		return mapper;
	}

	@Override
	public void run(String... args) throws Exception {
		String pass = passwordEncoder.encode("pooja");
		System.out.println("encodaed value "+pass);

	}
}
