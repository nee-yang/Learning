package com.example.demo.service;
import com.example.demo.model.Students;


public interface studentsService {

    Students get(int id);

    void update(Students model);

    int delete(int id);
}
