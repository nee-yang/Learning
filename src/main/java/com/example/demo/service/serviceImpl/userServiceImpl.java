package com.example.demo.service.serviceImpl;

import com.example.demo.model.User;
import com.example.demo.repository.UserMapper;
import com.example.demo.repository.studentsRepository.StudentsMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class userServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper=userMapper;
    }

    @Override
    public List<User> queryUserWithOrder() {
        return userMapper.queryUserWithOrder();
    }
}
