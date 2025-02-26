package com.juandavyc.SpringSecEx.controller;

import com.juandavyc.SpringSecEx.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "joe", 3),
                    new Student(2, "mae", 2),
                    new Student(3, "liam", 4)
            )
    );

    @GetMapping
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping
    public Student addStudent(
            @RequestBody Student student
    ) {
        students.add(student);
        return student;
    }
}
