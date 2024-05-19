package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CandidateJobDto;
import iiproject.jobhunting.dto.CompanyDto;
import iiproject.jobhunting.dto.JobDescriptionDto;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.entities.JobDescription;
import org.springframework.security.core.Authentication;
import iiproject.jobhunting.entities.User;

import java.util.List;
import java.util.Set;


public interface RecruiterService {
    boolean confirmEmailAndSave(String email, User2Dto user2Dto);

    boolean confirmCompanyAndSave(String email, CompanyDto companyDto);

    boolean confirmJobDescriptionAndSave(JobDescriptionDto jobDescriptionDto);

    JobDescription getJobDescriptionByID(int theId);

    boolean deleteJobDescription(JobDescriptionDto jobDescriptionDto);

    List<JobDescription> getJobDescriptionList(String email);

    List<CandidateJobDto> getCandidateJobList(String email);

    List<CandidateJobDto> getJdCandidatesById(int theId);

    boolean saveJobDescription(JobDescriptionDto jobDescriptionDto, Authentication authentication);
}
