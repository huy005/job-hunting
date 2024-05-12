package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CompanyDto;
import iiproject.jobhunting.dto.JobDescriptionDto;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.entities.JobDescription;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface RecruiterService {
    boolean confirmEmailAndSave(String email, User2Dto user2Dto);

    boolean confirmCompanyAndSave(String email, CompanyDto companyDto);

    List<JobDescription> getJobDescriptionList(String email);

    boolean saveJobDescription(JobDescriptionDto jobDescriptionDto, Authentication authentication);
}
