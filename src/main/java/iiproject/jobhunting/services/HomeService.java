package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.dto.UserDto;

import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.Role;
import iiproject.jobhunting.entities.User;

import java.util.List;


public interface HomeService {
    List<JobDescription> getJobDescriptionsByQuantity();

    boolean confirmLogIn(UserDto theUserDto);

    boolean addNewUser(UserDto theUserDto);

    User findUserByEmail(String theEmail);

    List<CompanyJdDto> searchCompanyJd(String keyword);

    boolean confirmEmailAndSave(String email, User2Dto user2Dto);

    boolean verifyUserByToken(String token);
}
