package iiproject.jobhunting.controllers;

import iiproject.jobhunting.dto.CompanyDto;
import iiproject.jobhunting.dto.GenericResponse;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.entities.Company;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.exception.UserNotFoundException;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.services.CompanyService;
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

@Controller
@RequestMapping("/recruiters")
public class RecruiterController {

    private final HttpSession httpSession;
    private final CompanyService companyService;

    @Autowired
    public RecruiterController(HttpSession httpSession, CompanyService companyService) {
        this.httpSession = httpSession;
        this.companyService = companyService;
    }

    @PostMapping("/recruiter-info")
    public @ResponseBody ResponseEntity<GenericResponse> putRecruiterInfo(@RequestBody @Valid User2Dto user2Dto,
                                                                          Authentication authentication,
                                                                          HttpStatus httpStatus) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean emailConfirmed = companyService.confirmEmailAndSave(userDetails.getUsername(), user2Dto);
        if (emailConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The recruiter's information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The updating user not found.");
    }

    @PostMapping("/company-info")
    public @ResponseBody ResponseEntity<GenericResponse> putCompanyInfo(@RequestBody @Valid CompanyDto companyDto,
                                                                        Authentication authentication,
                                                                        HttpStatus httpStatus) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean companyConfirmed = companyService.confirmCompanyAndSave(userDetails.getUsername(), companyDto);
        if (companyConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The company's information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The updating company not found.");
    }

    @GetMapping("/job-descriptions")
    public String getJobDescriptions(
            Authentication authentication, HttpStatus httpStatus, Model theModel) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<JobDescription> jobDescriptionList = companyService.getJobDescriptionList(userDetails.getUsername());
//        Company company = companyService.getCompany(userDetails.getUsername());
        if (jobDescriptionList != null) {
            theModel.addAttribute("jobDescriptionList",jobDescriptionList);
//            theModel.addAttribute("company",company);
            return "job-descriptions";
        }
        throw new UserNotFoundException("The company not found.");
    }

}
