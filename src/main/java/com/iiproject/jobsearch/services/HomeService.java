package com.iiproject.jobsearch.services;

import com.iiproject.jobsearch.dto.UserDto;
import com.iiproject.jobsearch.entities.*;

import java.util.List;
import java.util.Set;


public interface HomeService {
    List<Recruitment> getRecruitmentByQuantity();
    void save(User user);
    boolean confirmLogIn(UserDto theUserDto);
    void saveUserAndRole(User theUser, Role role);
    User findByID(int id);
}
