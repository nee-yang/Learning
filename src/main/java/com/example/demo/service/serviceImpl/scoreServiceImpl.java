package com.example.demo.service.serviceImpl;

import com.example.demo.repository.ScoreMapper;
import com.example.demo.repository.StudentMapper;
import com.example.demo.service.scoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("scoreService")
public class scoreServiceImpl implements scoreService {


    private ScoreMapper scoreMapper;

    @Autowired
    public void setScoreMapper(ScoreMapper scoreMapper) {this.scoreMapper=scoreMapper;}

    @Override
    public double getSumScores(int cid) {
        return scoreMapper.getSumScores(cid);
    }

    @Override
    public List<Map<Object, Object>> getMaxAndMinScores() {
        return scoreMapper.getMaxAndMinScores();
    }
}
