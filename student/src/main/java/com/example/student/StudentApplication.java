package com.example.student;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.student.model.StudentRepository;
import com.example.student.model.student;

@SpringBootApplication
public class StudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	@Bean
	ApplicationRunner init(StudentRepository repository) {
		return args -> {
			repository.save(new student("John Doe", "john.doe@example.com", "Computer Science"));
			repository.save(new student("Jane Smith", "jane.smith@example.com", "Business Administration"));
			repository.save(new student("Alice Johnson", "alice.johnson@example.com", "Electrical Engineering"));
			repository.save(new student("Bob Brown", "bob.brown@example.com", "Mathematics"));
			repository.save(new student("Charlie White", "charlie.white@example.com", "Psychology"));
			repository.findAll().forEach(System.out::println);
		};
	}

}
