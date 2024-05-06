package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CompanyDto;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.entities.Company;
import iiproject.jobhunting.entities.JobDescription;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface CompanyService {
    //    List<JobDescription> getRecruitmentByQuantity();
//    void getUserWithId(UserDto theUserDto);
    boolean confirmEmailAndSave(String email, User2Dto user2Dto);
    boolean confirmCompanyAndSave(String email, CompanyDto companyDto);
    List<JobDescription> getJobDescriptionList(String email);
//    Company getCompany(String email);
}
