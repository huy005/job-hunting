package iiproject.jobhunting.controllers;

import iiproject.jobhunting.dto.*;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.exception.UserNotFoundException;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.services.HomeService;
import iiproject.jobhunting.services.RecruiterService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/recruiters")
public class RecruiterController {

    private final HttpSession httpSession;
    private final RecruiterService recruiterService;
    private final HomeService homeService;

    @Autowired
    public RecruiterController(HttpSession httpSession, RecruiterService recruiterService, HomeService homeService) {
        this.httpSession = httpSession;
        this.recruiterService = recruiterService;
        this.homeService = homeService;
    }

    @PostMapping("/company-info")
    public @ResponseBody ResponseEntity<GenericResponse> putCompanyInfo(@RequestBody @Valid CompanyDto companyDto,
                                                                        Authentication authentication,
                                                                        HttpStatus httpStatus) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean companyConfirmed = recruiterService.confirmCompanyAndSave(userDetails.getUsername(), companyDto);
        if (companyConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The company's information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The updating company not found.");
    }

    //    JOB DESCRIPTION LIST
    @GetMapping("/job-descriptions")
    public String getJobDescriptions(
            Authentication authentication, HttpStatus httpStatus, Model theModel) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<JobDescription> jobDescriptionList = recruiterService.getJobDescriptionList(userDetails.getUsername());

        if (jobDescriptionList != null) {
            theModel.addAttribute("jobDescriptionList", jobDescriptionList);
            List<JobDescription> jobDescriptionQuantities = homeService.getJobDescriptionsByQuantity();
            if (!jobDescriptionQuantities.isEmpty()){
                theModel.addAttribute("jobDescriptionQuantities", jobDescriptionQuantities);
                return "job-descriptions";
            }else{
                throw new UserNotFoundException("The job description quantities not found.");
            }
        }
        throw new UserNotFoundException("The company not found.");
    }

    // UPDATE JOB DESCRIPTION
    @GetMapping("/job-description-update")
    public String update(@RequestParam("jobDescriptionId") int theId, Model theModel) {
        JobDescription jobDescription = recruiterService.getJobDescriptionByID(theId);
        theModel.addAttribute("jobDescription", jobDescription);
        return "jd/jd-update-form";
    }


    //    ADD A JOB DESCRIPTION
    @PostMapping("/job-description-registration")
    public @ResponseBody ResponseEntity<GenericResponse> saveUser(@RequestBody @Valid JobDescriptionDto jobDescriptionDto,
                                                                  Authentication authentication, HttpStatus httpStatus) {
        boolean isExistedJobDescription = recruiterService.saveJobDescription(jobDescriptionDto, authentication);
        if (isExistedJobDescription) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The company's information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The user not found.");
    }

    @PostMapping("/job-description-info")
    public @ResponseBody ResponseEntity<GenericResponse> putJobDescriptionInfo(@RequestBody @Valid JobDescriptionDto jobDescriptionDto,
                                                                               HttpStatus httpStatus) {
        boolean companyConfirmed = recruiterService.confirmJobDescriptionAndSave(jobDescriptionDto);
        if (companyConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The job description's information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The updating job description not found.");
    }

    //    DELETE A JOB DESCRIPTION
    @PostMapping("/job-description-delete")
    public @ResponseBody ResponseEntity<GenericResponse> deleteJobDescriptionInfo(@RequestBody JobDescriptionDto jobDescriptionDto,
                                                                                  HttpStatus httpStatus) {
        boolean companyConfirmed = recruiterService.deleteJobDescription(jobDescriptionDto);
        if (companyConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The job description's deleted successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The deleting job description not found.");
    }

    //  CANDIDATE LIST
    @GetMapping("/candidates")
    public String getCandidates(
            Authentication authentication, HttpStatus httpStatus, Model theModel) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<CandidateJdDto> candidateJobs = recruiterService.getCandidateJobList(userDetails.getUsername());
        if (!candidateJobs.isEmpty()) {
            theModel.addAttribute("candidateJobs", candidateJobs);
            return "candidate/candidates";
        }
        throw new UserNotFoundException("The user not found.");
    }

    //  CANDIDATES FROM JOB DESCRIPTION
    @GetMapping("/jd-candidates")
    public String getJdCandidates(
            @RequestParam("jobDescriptionId") int theId, Model theModel) {
        List<CandidateJdDto> candidateJobs = recruiterService.getJdCandidatesById(theId);
        if (!candidateJobs.isEmpty()) {
            theModel.addAttribute("candidateJobs", candidateJobs);
            return "candidate/jd-candidates";
        }
        throw new UserNotFoundException("The job description not found.");
    }
}

