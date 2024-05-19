package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CandidateJobDto;
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

import java.time.LocalDateTime;
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
    public boolean confirmEmailAndSave(String email, User2Dto user2Dto) {
        User theUser = findByEmail(email);
        if (theUser != null) {
            theUser.setUsername(user2Dto.getUsername());
            theUser.setUserAddress(user2Dto.getUserAddress());
            theUser.setPhoneNumber(user2Dto.getUserPhoneNumber());
            theUser.setUserDescription(user2Dto.getUserDescription());
            theUser.setUserImage(user2Dto.getUserImage());
            userRepository.save(theUser);
            return true;
        }
        return false;
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
            Category category = new Category(parseInt(jobDescriptionDto.getCategoryId()));
            jobDescription.setCategory(category);
            jobDescription.setDescription(jobDescriptionDto.getDescription());
            jobDescriptionRepository.save(jobDescription);
            return true;
        }
        return false;
    }


    @Override
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
    public List<CandidateJobDto> getCandidateJobList(String email) {
        List<JobDescription> jobDescriptions = getJobDescriptionList(email);
        if (!jobDescriptions.isEmpty()){
            List<CandidateJobDto> candidateJobs = new ArrayList<>();
            jobDescriptions.forEach(jobDescription -> {
                if (jobDescription.getDeleteStatus() == 0) {
                    CandidateJobDto candidateJobDto = new CandidateJobDto();
                    candidateJobDto.setTitle(jobDescription.getTitle());
                    candidateJobDto.setJobDescriptionAddress(jobDescription.getJobDescriptionAddress());
                    candidateJobDto.setJobDescriptionType(jobDescription.getJobDescriptionType());
                    candidateJobDto.setPosition(jobDescription.getPosition());
                    candidateJobDto.setDescription(jobDescription.getDescription());
                    candidateJobDto.setDeadline(jobDescription.getDeadline());
                    jobDescription.getCandidates().forEach(candidate -> {
                        if (candidate.getDeleteStatus() == 0) {
                            candidateJobDto.setUsername(candidate.getUsername());
                            candidateJobDto.setEmail(candidate.getEmail());
                            candidateJobDto.setUserAddress(candidate.getUserAddress());
                            candidateJobDto.setPhoneNumber(candidate.getPhoneNumber());
                        }
                    });
                    candidateJobs.add(candidateJobDto);
                }
            });
            return candidateJobs;
        }
        return null;
    }

    @Override
    public List<CandidateJobDto> getJdCandidatesById(int theId) {
        JobDescription jobDescription = findJobDescriptionById(theId);
        if (jobDescription != null) {
            List<CandidateJobDto> candidateJobDtos = new ArrayList<>();
            CandidateJobDto candidateJobDto = null;
            List<User> candidates = jobDescription.getCandidates();
            if (!candidates.isEmpty()) {
                for(User candidate : jobDescription.getCandidates()){
                    if (candidate.getDeleteStatus() == 0) {
                        candidateJobDto =new CandidateJobDto();
                        candidateJobDto.setTitle(jobDescription.getTitle());
                        candidateJobDto.setJobDescriptionAddress(jobDescription.getJobDescriptionAddress());
                        candidateJobDto.setJobDescriptionType(jobDescription.getJobDescriptionType());
                        candidateJobDto.setPosition(jobDescription.getPosition());
                        candidateJobDto.setDescription(jobDescription.getDescription());
                        candidateJobDto.setDeadline(jobDescription.getDeadline());
                        candidateJobDto.setUserId(candidate.getUserId());
                        candidateJobDto.setUsername(candidate.getUsername());
                        candidateJobDto.setEmail(candidate.getEmail());
                        candidateJobDto.setUserAddress(candidate.getUserAddress());
                        candidateJobDto.setPhoneNumber(candidate.getPhoneNumber());
                        candidateJobDto.setCv(candidate.getCv());
                        candidateJobDtos.add(candidateJobDto);
                    }
                }
            }else{
                candidateJobDto =new CandidateJobDto();
                candidateJobDto.setTitle(jobDescription.getTitle());
                candidateJobDto.setJobDescriptionAddress(jobDescription.getJobDescriptionAddress());
                candidateJobDto.setJobDescriptionType(jobDescription.getJobDescriptionType());
                candidateJobDto.setPosition(jobDescription.getPosition());
                candidateJobDto.setDescription(jobDescription.getDescription());
                candidateJobDto.setDeadline(jobDescription.getDeadline());
                candidateJobDto.setUserId(0);
                candidateJobDtos.add(candidateJobDto);
            }
            return candidateJobDtos;
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
        Category category = new Category(parseInt(jobDescriptionDto.getCategoryId()));
        jobDescription.setCategory(category);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = homeService.findUserByEmail(userDetails.getUsername());
        if (user != null) {
            jobDescription.setCompany(user.getCompany());
            jobDescriptionRepository.save(jobDescription);
            return true;
        }
        return false;
    }

}
