package iiproject.jobhunting.controllers;

import iiproject.jobhunting.dto.*;
import iiproject.jobhunting.exception.UserNotFoundException;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.services.CandidateService;
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

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

 @PostMapping("/job-application")
 public @ResponseBody ResponseEntity<GenericResponse> postJobApplication(@RequestBody AppliedJobDto appliedJobDto,
                                                                     Authentication authentication,
                                                                     HttpStatus httpStatus) {
     UserDetails userDetails = (UserDetails) authentication.getPrincipal();
     boolean jobApplied = candidateService.findUserAndSaveJobApplicaiton(userDetails.getUsername(), appliedJobDto);
     if (jobApplied) {
         return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                 "Successfully applied for the job.", Utils.getTimeStampHelper()));
     }
     throw new UserNotFoundException("The user applying for the job not found.");
 }

    @PostMapping("/favorite-job")
    public @ResponseBody ResponseEntity<GenericResponse> addFavoriteJob(@RequestBody FavoriteJobCompanyDto favoriteJobCompanyDto,
                                                                            Authentication authentication,
                                                                            HttpStatus httpStatus) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean jobAdded = candidateService.saveCandidateJob(userDetails.getUsername(), favoriteJobCompanyDto);
        if (jobAdded && favoriteJobCompanyDto.getFavoriteJobStatus() == 1) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "Successfully added the favorite job description to the list.", Utils.getTimeStampHelper()));
        }else if (jobAdded && favoriteJobCompanyDto.getFavoriteJobStatus() == 0){
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "Successfully deleted the favorite job description from the list.", Utils.getTimeStampHelper()));
        }else if (jobAdded && favoriteJobCompanyDto.getFavoriteJobStatus() == 3){
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "Successfully added the favorite company to the list.", Utils.getTimeStampHelper()));
        }else if (jobAdded && favoriteJobCompanyDto.getFavoriteJobStatus() == 2){
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "Successfully deleted the favorite company from the list.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The user not found.");
    }

}
