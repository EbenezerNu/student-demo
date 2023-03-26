package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final String Done = "Done";

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping({"/{studentID}"})
    public Student getStudent(@PathVariable("studentID") Long studentID) {
        return studentService.getStudent(studentID);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "/{studentID}")
    public void deleteStudent(
            @PathVariable("studentID") Long studentID
    ) {
        studentService.deleteStudent(studentID);
    }

    @PutMapping(path = "/{studentID}")
    public void putStudent(
            @PathVariable("studentID") Long studentID,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        studentService.updateStudent(studentID, name, email);
    }


}
