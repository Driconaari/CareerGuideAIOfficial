package com.careerguideaiofficial.service;

import com.careerguideaiofficial.model.Activity;
import com.careerguideaiofficial.model.User;
import com.careerguideaiofficial.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getRecentActivitiesForUser(User user) {
        return activityRepository.findTop10ByUserOrderByTimestampDesc(user);
    }
}