package com.example.student.controller;

import com.example.student.model.student;
import com.example.student.model.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class studentController {

    @Autowired
    private StudentRepository studentRepository;

    // 1. 取得所有學生資料 (GET)

    @GetMapping("/students")
    public List<student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/student")
    public ResponseEntity<List<student>> getAllCourses() {
        try {
            List<student> courses = new ArrayList<student>();
            studentRepository.findAll().forEach(courses::add);
            if (courses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/studentsa")
    public ResponseEntity<List<student>> getAllStudents2() {
        List<student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // 2. 依據 ID 取得特定學生資料 (GET)
    @GetMapping("/{id}")
    public Optional<student> getStudentById(@PathVariable Integer id) {
        return studentRepository.findById((long) id);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<student> getCourseById(@PathVariable("id") long id) {
        student existingStudent = studentRepository.findById(id).orElse(null);
        return new ResponseEntity<>(existingStudent, HttpStatus.OK);
    }

    // 3. 新增學生資料 (POST)
    @PostMapping
    // object改student 的話就.body()但不能有字串
    public ResponseEntity<Object> createStudent(@RequestBody student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Student with this email already exists.");
        }
        student savedStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    // 4. 更新學生資料 (PUT)
    @PutMapping("/{id}")
    public student updateStudent(@PathVariable Integer id, @RequestBody student studentDetails) {
        return studentRepository.findById((long) id).map(student -> {
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setDepartment(studentDetails.getDepartment());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<student> updateCourse(@PathVariable("id") long id, @RequestBody student stu) {
        student existingStudent = studentRepository.findById(id).orElse(null);
        existingStudent.setName(stu.getName());
        existingStudent.setEmail(stu.getEmail());
        existingStudent.setDepartment(stu.getDepartment());
        studentRepository.save(existingStudent);
        return new ResponseEntity<>(existingStudent, HttpStatus.OK);
    }

    // 5. 刪除學生資料 (DELETE)
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentRepository.deleteById((long) id);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Long> deleteCourse(@PathVariable("id") long id) {
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
