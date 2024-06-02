package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.User2Dto;

import iiproject.jobhunting.dto.UserDto;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface HomeService {
    List<JobDescription> getJobDescriptionsByQuantity();

    boolean addNewUser(UserDto theUserDto);

    User findUserByEmail(Authentication authentication);

    List<CompanyJdDto> searchCompanyJd(String keyword);

    boolean confirmEmailAndSave(Authentication authentication, User2Dto user2Dto);

    boolean verifyUserByToken(String token);

    boolean sendOtpAuthentication(Authentication authentication);

    boolean verifyOtp(UserDto userDto, Authentication authentication);
}
