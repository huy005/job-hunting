package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CandidateJdDto;
import iiproject.jobhunting.dto.CompanyDto;
import iiproject.jobhunting.dto.JobDescriptionDto;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.entities.Category;
import iiproject.jobhunting.entities.Company;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.repositories.CompanyRepository;
import iiproject.jobhunting.repositories.JobDescriptionRepository;
import iiproject.jobhunting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.lang.Integer.parseInt;

@Service
public class RecruiterServiceImp implements RecruiterService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final HomeService homeService;
    private final JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    public RecruiterServiceImp(UserRepository userRepository,
                               CompanyRepository companyRepository, HomeService homeService, JobDescriptionRepository jobDescriptionRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.homeService = homeService;
        this.jobDescriptionRepository = jobDescriptionRepository;
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


    @Override
    public boolean confirmCompanyAndSave(String email, CompanyDto companyDto) {
        User theUser = findByEmail(email);
        if (theUser != null) {
            Company confirmedCompany = theUser.getCompany();
//            confirmedCompany.setCompanyEmail(companyDto.getCompanyEmail());
            confirmedCompany.setCompanyName(companyDto.getCompanyName());
            confirmedCompany.setCompanyAddress(companyDto.getCompanyAddress());
            confirmedCompany.setCompanyPhoneNumber(companyDto.getCompanyPhoneNumber());
            confirmedCompany.setCompanyDescription(companyDto.getCompanyDescription());
            confirmedCompany.setCompanyLogo(companyDto.getCompanyLogo());
            companyRepository.save(confirmedCompany);
            return true;
        }
        return false;
    }

    @Override
    public JobDescription getJobDescriptionByID(int theId) {
        return findJobDescriptionById(theId);
    }

    @Override
    public boolean deleteJobDescription(JobDescriptionDto jobDescriptionDto) {
        JobDescription jobDescription = findJobDescriptionById(jobDescriptionDto.getJobDescriptionId());
        if (jobDescription != null && jobDescription.getDeleteStatus() == 0) {
            jobDescription.setDeleteStatus(1);
            jobDescription.setDeletedAt(Utils.getLocalDateTimeOfNow());
            jobDescriptionRepository.save(jobDescription);
            return true;
        }
        return false;
    }

    @Override
    public boolean confirmJobDescriptionAndSave(JobDescriptionDto jobDescriptionDto) {
        JobDescription jobDescription = findJobDescriptionById(jobDescriptionDto.getJobDescriptionId());
        if (jobDescription != null) {
            jobDescription.setTitle(jobDescriptionDto.getTitle());
            jobDescription.setQuantity(jobDescriptionDto.getQuantity());
            jobDescription.setExperience(jobDescriptionDto.getExperience());
            jobDescription.setJobDescriptionAddress(jobDescriptionDto.getJobDescriptionAddress());
            jobDescription.setDeadline(jobDescriptionDto.getDeadline());
            jobDescription.setSalary(jobDescriptionDto.getSalary());
            jobDescription.setJobDescriptionType(jobDescriptionDto.getJobDescriptionType());
            Category category = new Category(jobDescriptionDto.getCategory().getCategoryId());
            jobDescription.setCategory(category);
            jobDescription.setDescription(jobDescriptionDto.getDescription());
            jobDescriptionRepository.save(jobDescription);
            return true;
        }
        return false;
    }


    @Override
    @Transactional
    public List<JobDescription> getJobDescriptionList(String email) {
        User theUser = findByEmail(email);
        if (theUser != null) {
            List<JobDescription> jobDescriptionList = new ArrayList<>();
            theUser.getCompany().getJobDescriptions().forEach(jobDescription -> {
                if (jobDescription.getDeleteStatus() == 0) {
                    jobDescriptionList.add(jobDescription);
                }
            });
            return jobDescriptionList;
        }
        return null;
    }

    @Override
    @Transactional
    public List<CandidateJdDto> getCandidateJobList(String email) {
        List<JobDescription> jobDescriptions = getJobDescriptionList(email);
        if (!jobDescriptions.isEmpty()){
            List<CandidateJdDto> candidateJobs = new ArrayList<>();
            jobDescriptions.forEach(jobDescription -> {
                if (jobDescription.getDeleteStatus() == 0) {
                    CandidateJdDto candidateJdDto = new CandidateJdDto();
                    candidateJdDto.setTitle(jobDescription.getTitle());
                    candidateJdDto.setJobDescriptionAddress(jobDescription.getJobDescriptionAddress());
                    candidateJdDto.setJobDescriptionType(jobDescription.getJobDescriptionType());
                    candidateJdDto.setPosition(jobDescription.getPosition());
                    candidateJdDto.setDescription(jobDescription.getDescription());
                    candidateJdDto.setDeadline(jobDescription.getDeadline());
                    jobDescription.getCandidates().forEach(candidate -> {
                        if (candidate.getDeleteStatus() == 0) {
                            candidateJdDto.setUsername(candidate.getUsername());
                            candidateJdDto.setEmail(candidate.getEmail());
                            candidateJdDto.setUserAddress(candidate.getUserAddress());
                            candidateJdDto.setPhoneNumber(candidate.getPhoneNumber());
                        }
                    });
                    candidateJobs.add(candidateJdDto);
                }
            });
            return candidateJobs;
        }
        return null;
    }

    @Override
    @Transactional
    public List<CandidateJdDto> getJdCandidatesById(int theId) {
        JobDescription jobDescription = findJobDescriptionById(theId);
        if (jobDescription != null) {
            List<CandidateJdDto> candidateJdDtos = new ArrayList<>();
            CandidateJdDto candidateJdDto = null;
            Set<User> candidates = jobDescription.getCandidates();
            if (!candidates.isEmpty()) {
                for(User candidate : jobDescription.getCandidates()){
                    if (candidate.getDeleteStatus() == 0) {
                        candidateJdDto =new CandidateJdDto();
                        candidateJdDto.setTitle(jobDescription.getTitle());
                        candidateJdDto.setJobDescriptionAddress(jobDescription.getJobDescriptionAddress());
                        candidateJdDto.setJobDescriptionType(jobDescription.getJobDescriptionType());
                        candidateJdDto.setPosition(jobDescription.getPosition());
                        candidateJdDto.setDescription(jobDescription.getDescription());
                        candidateJdDto.setDeadline(jobDescription.getDeadline());
                        candidateJdDto.setUserId(candidate.getUserId());
                        candidateJdDto.setUsername(candidate.getUsername());
                        candidateJdDto.setEmail(candidate.getEmail());
                        candidateJdDto.setUserAddress(candidate.getUserAddress());
                        candidateJdDto.setPhoneNumber(candidate.getPhoneNumber());
                        candidateJdDto.setCv(candidate.getCvSubmitted());
                        candidateJdDtos.add(candidateJdDto);
                    }
                }
            }else{
                candidateJdDto =new CandidateJdDto();
                candidateJdDto.setTitle(jobDescription.getTitle());
                candidateJdDto.setJobDescriptionAddress(jobDescription.getJobDescriptionAddress());
                candidateJdDto.setJobDescriptionType(jobDescription.getJobDescriptionType());
                candidateJdDto.setPosition(jobDescription.getPosition());
                candidateJdDto.setDescription(jobDescription.getDescription());
                candidateJdDto.setDeadline(jobDescription.getDeadline());
                candidateJdDto.setUserId(0);
                candidateJdDtos.add(candidateJdDto);
            }
            return candidateJdDtos;
        }
        return null;
    }

    @Override
    public boolean saveJobDescription(JobDescriptionDto jobDescriptionDto, Authentication authentication) {
        JobDescription jobDescription = new JobDescription();
        jobDescription.setTitle(jobDescriptionDto.getTitle());
        jobDescription.setQuantity(jobDescriptionDto.getQuantity());
        jobDescription.setExperience(jobDescriptionDto.getExperience());
        jobDescription.setJobDescriptionAddress(jobDescriptionDto.getJobDescriptionAddress());
        jobDescription.setDeadline(jobDescriptionDto.getDeadline());
        jobDescription.setSalary(jobDescriptionDto.getSalary());
        jobDescription.setJobDescriptionType(jobDescriptionDto.getJobDescriptionType());
        jobDescription.setDescription(jobDescriptionDto.getDescription());
        Category category = new Category(jobDescriptionDto.getCategory().getCategoryId());
        jobDescription.setCategory(category);
        User user = homeService.findUserByEmail(authentication);
        if (user != null) {
            jobDescription.setCompany(user.getCompany());
            jobDescriptionRepository.save(jobDescription);
            return true;
        }
        return false;
    }

}
