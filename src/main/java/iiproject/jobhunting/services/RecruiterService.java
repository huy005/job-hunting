package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CandidateJdDto;
import iiproject.jobhunting.dto.CompanyDto;
import iiproject.jobhunting.dto.JobDescriptionDto;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.entities.JobDescription;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface RecruiterService {


    boolean confirmCompanyAndSave(String email, CompanyDto companyDto);

    boolean confirmJobDescriptionAndSave(JobDescriptionDto jobDescriptionDto);

    JobDescription getJobDescriptionByID(int theId);

    boolean deleteJobDescription(JobDescriptionDto jobDescriptionDto);

    List<JobDescription> getJobDescriptionList(String email);

    List<CandidateJdDto> getCandidateJobList(String email);

    List<CandidateJdDto> getJdCandidatesById(int theId);

    boolean saveJobDescription(JobDescriptionDto jobDescriptionDto, Authentication authentication);
}
