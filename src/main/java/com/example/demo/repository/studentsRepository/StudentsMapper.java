package com.example.demo.repository.studentsRepository;

import com.example.demo.model.Students;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("studentsMapper")
public interface StudentsMapper {

    /**
     * 根据id获取Student对象
     * @param id
     * @return
     */
    Students get(int id);

    /**
     * 更新Student对象
     * @param student
     * @return
     */
    void update(Students student);
}
