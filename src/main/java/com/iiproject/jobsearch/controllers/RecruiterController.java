package com.iiproject.jobsearch.controllers;

import com.iiproject.jobsearch.dto.GenericResponse;
import com.iiproject.jobsearch.dto.User2Dto;
import com.iiproject.jobsearch.dto.UserDto;
import com.iiproject.jobsearch.entities.User;
import com.iiproject.jobsearch.exception.UserNotFoundException;
import com.iiproject.jobsearch.helpers.Utils;
import com.iiproject.jobsearch.services.CompanyService;
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
@RequestMapping("/recruiters")
public class RecruiterController {

    private final HttpSession httpSession;
    private final CompanyService companyService;

    @Autowired
    public RecruiterController(HttpSession httpSession, CompanyService companyService) {
        this.httpSession = httpSession;
        this.companyService = companyService;
    }

//    @GetMapping("/companyPage")
//    public String showCompanyPage(Model themodel) {
//        return "company-page";
//    }

    @PostMapping("/recruiter-info")
    public @ResponseBody ResponseEntity<GenericResponse> putPersonalInfo(@RequestBody @Valid User2Dto theUserDto,
                                                                         Authentication authentication,
                                                                         HttpStatus httpStatus) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean emailConfirmed = companyService.confirmEmailAndSave(userDetails.getUsername(),theUserDto);
        if (emailConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The updating user not found.");
    }

}
