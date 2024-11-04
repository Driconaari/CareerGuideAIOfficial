package com.careerguideaiofficial.repository;

import com.careerguideaiofficial.model.Activity;
import com.careerguideaiofficial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTop10ByUserOrderByTimestampDesc(User user);
}