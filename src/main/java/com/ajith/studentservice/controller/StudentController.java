package com.ajith.studentservice.controller;

import com.ajith.studentservice.model.Student;
import com.ajith.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAll(){
        return new ResponseEntity<>(studentService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable int id){
        return new ResponseEntity<>(studentService.get(id),HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Student> add(@RequestBody Student student){
        return new ResponseEntity<>(studentService.add(student),HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student){
        return new ResponseEntity<>(studentService.update(id, student),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> remove(@PathVariable int id){
        return new ResponseEntity<>(studentService.remove(id),HttpStatus.OK);
    }
}
