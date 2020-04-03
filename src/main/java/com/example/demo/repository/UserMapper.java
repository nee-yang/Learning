package com.example.demo.repository;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("userMapper")
public interface UserMapper {

    List<User> queryUserWithOrder();
}
