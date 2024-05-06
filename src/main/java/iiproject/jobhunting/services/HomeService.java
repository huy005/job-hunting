package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.UserDto;

import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.Role;
import iiproject.jobhunting.entities.User;

import java.util.List;


public interface HomeService {
    List<JobDescription> getJobDescriptionsByQuantity();
    void save(User user);
    boolean confirmLogIn(UserDto theUserDto);
    void saveUserAndRole(User theUser, Role role);
    User findByID(int id);
}