package com.example.demo.repository;

import com.example.demo.model.Student;
import com.example.demo.model.Students;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("studentMapper")
public interface StudentMapper {

    List<Student> getAll();

    int insertMultiple(List<Student> students);

    int insert(Student student);

}
