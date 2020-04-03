package com.example.demo.repository;


import com.example.demo.model.Students;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("scoreMapper")
public interface ScoreMapper  {

    double getSumScores(int cid);

    List<Map<Object,Object>> getMaxAndMinScores();
}
