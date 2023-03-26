package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return args -> {
            List<Student> allStudents = repository.findAll();
            if(allStudents.size() > 0) {
                return;
            }
            Student Eben = new Student(
                    1L, "Eben",
                    "ebenebe1997@gmail.com",
                    LocalDate.of(1997, Month.MAY, 23)
            );

            Student Prince = new Student(
                    "Prince",
                    "princeangellos@gmail.com",
                    LocalDate.of(1997, Month.MAY, 23)
            );

            Student Jonathan = new Student(
                    "Jonathan",
                    "jonathanstl@gmail.com",
                    LocalDate.of(1998, Month.OCTOBER, 23)
            );

            repository.saveAll(
                    List.of(Eben, Prince, Jonathan)
            );
        };
    }
}
