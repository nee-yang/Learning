package com.example.demo.service;

import java.util.List;
import java.util.Map;

public interface scoreService {
    double getSumScores(int cid);

    List<Map<Object,Object>> getMaxAndMinScores();
}
