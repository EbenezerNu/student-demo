package com.example.demo.student;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.
                findStudentByEmail(student.getEmail());
        System.out.println(student);
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentID) {
        boolean idExists = studentRepository.existsById(studentID);
        if (!idExists) {
            throw new IllegalStateException("Student ID '" + studentID + "' does not Exists");
        }
        studentRepository.deleteById(studentID);
    }

    @Transactional
    public void updateStudent(Long studentID, String name, String email) {
        boolean idExists = studentRepository.existsById(studentID);
        if (!idExists) {
            throw new IllegalStateException("Student ID '" + studentID + "' does not Exists");
        }

        Optional<Student> updateStd = studentRepository.findById(studentID);

        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(updateStd.get().getName(), name)) {
            updateStd.get().setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(updateStd.get().getEmail(), email)) {
            Optional<Student> studentOptionalEmail
                    = studentRepository.findStudentByEmail(email);
            if (studentOptionalEmail.isPresent()) {
                throw new IllegalStateException("This Email is taken");
            }
            updateStd.get().setEmail(email);
        }

    }

    public Student getStudent(Long studentID) {
        if(studentID == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Student ID");
        }
        Optional<Student> student = studentRepository.findById(studentID);
        if(student.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student ID '" + studentID + "' does not Exists");
//            throw new ObjectNotFoundException("Student ID '" + studentID + "' does not Exists");
        }
        return student.get();
    }
}
