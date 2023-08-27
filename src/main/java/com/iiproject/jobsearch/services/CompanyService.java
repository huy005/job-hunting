package com.iiproject.jobsearch.services;

import com.iiproject.jobsearch.entities.Recruitment;

import java.util.List;


public interface CompanyService {
    List<Recruitment> getRecruitmentByQuantity();
}
