package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.AppliedJobDto;
import iiproject.jobhunting.dto.FavoriteJobCompanyDto;
import iiproject.jobhunting.dto.JobDescriptionDto;
import iiproject.jobhunting.entities.JobDescription;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CandidateService {
    boolean findUserAndSaveJobApplicaiton(Authentication authentication, AppliedJobDto appliedJobDto);
    boolean saveCandidateCompanyJob(Authentication authentication, FavoriteJobCompanyDto favoriteJobCompanyDto);
    List<AppliedJobDto> getAppliedJobsByEmail(Authentication authentication);
    List<JobDescriptionDto> getFavoriteJobs(Authentication authentication);
}
