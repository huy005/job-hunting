package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.AppliedJobDto;
import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.FavoriteJobCompanyDto;

import java.util.List;

public interface CandidateService {
    boolean findUserAndSaveJobApplicaiton(String email, AppliedJobDto appliedJobDto);
    boolean saveCandidateJob(String email, FavoriteJobCompanyDto favoriteJobCompanyDto);
}
