package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.model.Students;

import java.util.List;

public interface studentService {
    List<Student> getAll();

    int insertMultiple(List<Student> students);

    int insert(Student student);

}
