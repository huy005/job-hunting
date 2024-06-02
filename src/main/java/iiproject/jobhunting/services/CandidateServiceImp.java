package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.AppliedJobDto;
import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.FavoriteJobCompanyDto;
import iiproject.jobhunting.dto.JobDescriptionDto;
import iiproject.jobhunting.entities.AppliedJob;
import iiproject.jobhunting.entities.Company;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.repositories.AppliedJobRepository;
import iiproject.jobhunting.repositories.CompanyRepository;
import iiproject.jobhunting.repositories.JobDescriptionRepository;
import iiproject.jobhunting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CandidateServiceImp implements CandidateService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final HomeService homeService;
    private final JobDescriptionRepository jobDescriptionRepository;
    private final AppliedJobRepository appliedJobRepository;

    @Autowired
    public CandidateServiceImp(UserRepository userRepository,
                               CompanyRepository companyRepository,
                               HomeService homeService,
                               JobDescriptionRepository jobDescriptionRepository,
                               AppliedJobRepository appliedJobRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.homeService = homeService;
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.appliedJobRepository = appliedJobRepository;
    }

    public User findByEmail(String email) {
        Optional<User> recruiter = userRepository.findByEmail(email);
        User theRecruiter = null;
        if (recruiter.isPresent()) theRecruiter = recruiter.get();
        return theRecruiter;
    }

    public JobDescription findJobDescriptionById(int theId) {
        Optional<JobDescription> jobDescription = jobDescriptionRepository.findById(theId);
        JobDescription theJobDescription = null;
        if (jobDescription.isPresent()) theJobDescription = jobDescription.get();
        return theJobDescription;
    }

    public Company findCompanyById(int theId) {
        Optional<Company> company = companyRepository.findById(theId);
        Company theCompany = null;
        if (company.isPresent()) theCompany = company.get();
        return theCompany;
    }

    @Override
    public boolean findUserAndSaveJobApplicaiton(Authentication authentication, AppliedJobDto appliedJobDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(userDetails.getUsername());
        JobDescription jobDescription = findJobDescriptionById(appliedJobDto.getJobDescriptionId());
        if (user != null && jobDescription != null) {
            AppliedJob appliedJob = new AppliedJob();
            appliedJob.setCvName(appliedJobDto.getCvName());
            appliedJob.setAdditionalInfo(appliedJobDto.getAdditionalInfo());
            appliedJob.setCreatedAt(Utils.getLocalDateTimeOfNow());
            appliedJob.setStatus(0);
            appliedJob.setJobDescription(jobDescription);
            appliedJob.setUser(user);
            appliedJobRepository.save(appliedJob);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean saveCandidateCompanyJob(Authentication authentication, FavoriteJobCompanyDto favoriteJobCompanyDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(userDetails.getUsername());
        JobDescription jobDescription = findJobDescriptionById(favoriteJobCompanyDto.getJobDescriptionId());
        Company company = findCompanyById(favoriteJobCompanyDto.getCompanyId());
        if (user != null && user.getDeleteStatus() == 0 && favoriteJobCompanyDto.getFavoriteJobStatus() == 1) {
            user.getJobDescriptions().add(jobDescription);
            return true;
        } else if (user != null && user.getDeleteStatus() == 0 && favoriteJobCompanyDto.getFavoriteJobStatus() == 0) {
            Set<JobDescription> userJds = user.getJobDescriptions();
            for (JobDescription userJd : userJds) {
                if (userJd.getJobDescriptionId() == favoriteJobCompanyDto.getJobDescriptionId()) {
                    userJds.remove(jobDescription);
                    return true;
                }
            }
        } else if (user != null && user.getDeleteStatus() == 0 && favoriteJobCompanyDto.getFavoriteJobStatus() == 3) {
            user.getCandidateCompanies().add(company);
            return true;
        } else if (user != null && user.getDeleteStatus() == 0 && favoriteJobCompanyDto.getFavoriteJobStatus() == 2) {
            Set<Company> userComs = user.getCandidateCompanies();
            for (Company userCom : userComs) {
                if (userCom.getCompanyId() == favoriteJobCompanyDto.getCompanyId()) {
                    userComs.remove(company);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional
    public List<AppliedJobDto> getAppliedJobsByEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(userDetails.getUsername());
        if (user != null && user.getDeleteStatus() == 0) {
            List<AppliedJobDto> appliedJobDtos = new ArrayList<>();
            AppliedJobDto appliedJobDto = new AppliedJobDto();
            user.getAppliedJobs().forEach(appliedJob -> {
                JobDescription jobDescription = appliedJob.getJobDescription();
                if (jobDescription != null && jobDescription.getDeleteStatus() == 0){
                    appliedJobDto.setJobDescription(jobDescription);
                    appliedJobDtos.add(appliedJobDto);
                }
            });
            appliedJobDto.setAppliedJobId(0);
            appliedJobDtos.add(appliedJobDto);
            return appliedJobDtos;
        }
        return List.of();
    }

    @Override
    @Transactional
    public List<JobDescriptionDto> getFavoriteJobs(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(userDetails.getUsername());
        if (user != null && user.getDeleteStatus() == 0) {
            List<JobDescriptionDto> jobDescriptionDtos = new ArrayList<>();
            JobDescriptionDto jobDescriptionDto = new JobDescriptionDto();
            user.getJobDescriptions().forEach(jobDescription -> {
                if (jobDescription != null && jobDescription.getDeleteStatus() == 0){
                    jobDescriptionDto.setJobDescriptionId(jobDescription.getJobDescriptionId());
                    jobDescriptionDto.setJobDescriptionAddress(jobDescription.getJobDescriptionAddress());
                    jobDescriptionDto.setTitle(jobDescription.getTitle());
                    jobDescriptionDto.setQuantity(jobDescription.getQuantity());
                    jobDescriptionDto.setDeadline(jobDescription.getDeadline());
                    jobDescriptionDto.setCategory(jobDescription.getCategory());
                    jobDescriptionDto.setDescription(jobDescription.getDescription());
                    jobDescriptionDto.setSalary(jobDescription.getSalary());
                    jobDescriptionDto.setPosition(jobDescription.getPosition());
                    jobDescriptionDto.setJobDescriptionType(jobDescription.getJobDescriptionType());
                    jobDescriptionDto.setExperience(jobDescription.getExperience());
                    jobDescriptionDto.setCompany(jobDescription.getCompany());
                    jobDescriptionDtos.add(jobDescriptionDto);
                }
            });
            jobDescriptionDto.setJobDescriptionId(0);
            jobDescriptionDtos.add(jobDescriptionDto);
            return jobDescriptionDtos;
        }
        return List.of();
    }
}
