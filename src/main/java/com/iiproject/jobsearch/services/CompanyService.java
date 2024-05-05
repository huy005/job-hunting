package com.iiproject.jobsearch.services;

import com.iiproject.jobsearch.dto.CompanyDto;
import com.iiproject.jobsearch.dto.GenericResponse;
import com.iiproject.jobsearch.dto.User2Dto;
import com.iiproject.jobsearch.dto.UserDto;
import com.iiproject.jobsearch.entities.Recruitment;
import com.iiproject.jobsearch.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CompanyService {
    //    List<Recruitment> getRecruitmentByQuantity();
//    void getUserWithId(UserDto theUserDto);
    boolean confirmEmailAndSave(String email, User2Dto user2Dto);
    boolean confirmCompanyAndSave(String email, CompanyDto companyDto);
}
