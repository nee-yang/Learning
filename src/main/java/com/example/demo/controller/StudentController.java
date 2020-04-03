package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.RedisConfig;
import com.example.demo.model.Student;
import com.example.demo.model.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.studentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private studentService studentService;

    @Autowired
    public void setStudentService(studentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/getAllStudentsInfo")
    public List<Student> get() {
        List<Student> studentList=studentService.getAll();
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", student.getId());
//        jsonObject.put("age", student.getAge());
//        jsonObject.put("name", student.getName());
//        RedisConfig redisConfig = new RedisConfig("100");
//        redisConfig.putObject("frank",jsonObject.toString());
        int i=1;
        return studentList;
    }


    @RequestMapping("/insertMultiple")
    public int put() {
        Student student1=new Student();
        student1.setSid(9);
        student1.setSname("nine");
        student1.setDate_of_birth(new Date());
        student1.setGender("男ba");
        Student student2=new Student();
        student2.setSid(8);
        student2.setSname("eight");
        student2.setDate_of_birth(new Date());
        student2.setGender("女");
        List<Student> students=new ArrayList<Student>();
        students.add(student1);
        students.add(student2);
        int result=studentService.insertMultiple(students);
        return result;
    }

    @RequestMapping("/insert")
    public int pust() {
        Student student1=new Student();
        student1.setSid(7);
        student1.setSname("seven");
        student1.setDate_of_birth(new Date());
        student1.setGender("男");
        int result=studentService.insert(student1);
        return result;
    }



}
