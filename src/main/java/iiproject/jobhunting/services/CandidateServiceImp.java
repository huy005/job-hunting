package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.AppliedJobDto;
import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.FavoriteJobCompanyDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public boolean findUserAndSaveJobApplicaiton(String email, AppliedJobDto appliedJobDto) {
        User user = findByEmail(email);
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
    public boolean saveCandidateJob(String email, FavoriteJobCompanyDto favoriteJobCompanyDto) {
        User user = findByEmail(email);
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
}
