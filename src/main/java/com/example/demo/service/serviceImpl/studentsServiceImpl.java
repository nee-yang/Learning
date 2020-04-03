package com.example.demo.service.serviceImpl;

import com.example.demo.model.Students;
import com.example.demo.repository.studentsRepository.StudentsMapper;
import com.example.demo.service.studentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentsService")
public class studentsServiceImpl implements studentsService {

//    @Autowired
    private StudentsMapper studentMapper;

    @Autowired
    public void setStudentMapper(StudentsMapper studentMapper) {
        this.studentMapper=studentMapper;
    }


    @Override
    public Students get(int id) {


        return studentMapper.get(id);
    }

    @Override
    public void update(Students model) {

        studentMapper.update(model);

    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
