package com.example.demo.service.serviceImpl;

import com.example.demo.model.Student;
import com.example.demo.model.Students;
import com.example.demo.repository.StudentMapper;
import com.example.demo.repository.studentsRepository.StudentsMapper;
import com.example.demo.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class studentServiceImpl implements studentService {


    private StudentMapper studentMapper;

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper=studentMapper;
    }


    @Override
    public List<Student> getAll() {
        return studentMapper.getAll();
    }

    @Override
    public int insertMultiple(List<Student> students) {
        return studentMapper.insertMultiple(students);
    }

    @Override
    public int insert(Student student) {
        return studentMapper.insert(student);
    }
}
