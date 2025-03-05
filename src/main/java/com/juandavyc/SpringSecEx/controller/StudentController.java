package com.juandavyc.SpringSecEx.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final List<String> students = new ArrayList<>(
            List.of(
                    "ana",
                    "jose",
                    "maria"
            )
    );

    @GetMapping
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<String>> getStudents() {
        return ResponseEntity.ok(students);
    }

    @GetMapping(path = "{student}")
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<String> getStudent(
            @PathVariable String student
    ) {
        final var studentEntity = students.stream().filter(stu -> stu.equalsIgnoreCase(student))
                .findFirst()
                .orElse(null);

        return ResponseEntity.ok(studentEntity);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<String> addStudent(
            @RequestBody String student
    ) {
        students.add(student.trim().toLowerCase());
        return ResponseEntity.created(
                URI.create(("/").concat(students.get(students.size() - 1)))
        ).build();
    }

    @PutMapping(path = "{username}")
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<List<String>> updateStudent(
            @PathVariable String username,
            @RequestBody String student
    ) {
        students.set(students.indexOf(username), student.trim().toLowerCase());
        return ResponseEntity.ok(students);
    }

    @DeleteMapping(path = "{username}")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<List<String>> deleteStudent(
            @PathVariable String username
    ) {
        students.remove(username);
        return ResponseEntity.noContent().build();
    }
}
