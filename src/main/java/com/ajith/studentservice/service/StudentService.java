package com.ajith.studentservice.service;

import com.ajith.studentservice.model.Student;
import com.ajith.studentservice.repo.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public List<Student> getAll(){
        List<Student> all = studentRepo.findAll();
        logger.info("{} ",all);
        return all;
    }

    public Student get(int id){
        Student student = studentRepo.findById(id).get();
        logger.info("{} ",student);
        return student;
    }
    public Student update(int id, Student student){
        Student existing = studentRepo.findById(id).get();
        existing.setName(student.getName());
        existing.setAge(student.getAge());
        existing.setCity(student.getCity());
        existing.setEmail(student.getEmail());
        Student update = studentRepo.save(existing);
        logger.info("{} ",update);
        return update;
    }
    public Student add(Student student){
        Student addStudent = studentRepo.save(student);
        logger.info("{} ",addStudent);
        return addStudent;
    }
    public String remove(int id){
        studentRepo.deleteById(id);
        logger.info("Student deleted with i: {}",id);
        return "Student removed with id "+id;
    }

}
