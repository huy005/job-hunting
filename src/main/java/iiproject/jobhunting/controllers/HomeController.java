package iiproject.jobhunting.controllers;

import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.GenericResponse;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.dto.UserDto;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import iiproject.jobhunting.exception.UserNotFoundException;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.services.HomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    //LOGIN PAGE
    @GetMapping("/log-in-page")
    public String showLogInPage(Model theModel) {
        theModel.addAttribute("userDtoLogIn", new UserDto());
        return "log-in";
    }

    //VERIFICATION PAGE
    @GetMapping("/verification-user")
    public String verifyRegisteredUser(@RequestParam String token, Model theModel) {
        if (homeService.verifyUserByToken(token)) {
            theModel.addAttribute("userDtoLogIn", new UserDto());
            return "verification/verification-user-page";
        }
        throw new UsernameNotFoundException("The user not verified yet.");
    }

    @GetMapping("/")
    public String showMainHomePage1(Model theModel, Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = homeService.findUserByEmail(userDetails.getUsername());
            if (user != null) {
                List<JobDescription> jobDescriptionQuantities = homeService.getJobDescriptionsByQuantity();
                if (!jobDescriptionQuantities.isEmpty()) {
                    theModel.addAttribute("jobDescriptionQuantities", jobDescriptionQuantities);
                }
                theModel.addAttribute("user", user);
                theModel.addAttribute("company", user.getCompany());
                return "home";
            }
            throw new UsernameNotFoundException("The user not found.");
        }
        List<JobDescription> jobDescriptionQuantities = homeService.getJobDescriptionsByQuantity();
        theModel.addAttribute("jobDescriptionQuantities", jobDescriptionQuantities);
        return "index";
    }

    @GetMapping("/index")
    public String showHomePage(Model theModel) {
        List<JobDescription> jobDescriptionQuantities = homeService.getJobDescriptionsByQuantity();
        theModel.addAttribute("jobDescriptionQuantities", jobDescriptionQuantities);
        return "index";
    }

    @GetMapping("/home")
    public String showMainHomePage2(Model model, Authentication authentication) {
        List<JobDescription> jobDescriptionQuantities = homeService.getJobDescriptionsByQuantity();
        model.addAttribute("jobDescriptionQuantities", jobDescriptionQuantities);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = homeService.findUserByEmail(userDetails.getUsername());
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("company", user.getCompany());
        } else {
            throw new UsernameNotFoundException("The user not found.");
        }
        return "home";
    }

    //    ADD NEW USERS
    @PostMapping("/user-registration")
    public @ResponseBody ResponseEntity<GenericResponse> addNewUser(@RequestBody UserDto theUserDto, HttpStatus
            httpStatus) {
        if (homeService.addNewUser(theUserDto)) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The user registered successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("Failed to register the user.");
    }

    // SEACH JOB DESCRIPTION
    @GetMapping("/company-jd-info-page")
    public String showCompanyJdPage(Model theModel) {
//        List<CompanyJdDto> companyJdDtos = homeService.searchCompanyJd(keyword);
//        theModel.addAttribute("companyJdDtos", companyJdDtos);
        return "jd/company-jd-search";
    }

    @GetMapping("/company-jd-info")
    public String searchCompanyJd(@RequestParam(required = false, name = "keyword") String keyword, Model theModel) {
        List<CompanyJdDto> companyJdDtos = homeService.searchCompanyJd(keyword);
        theModel.addAttribute("companyJdDtos", companyJdDtos);
        return "jd/company-jd-search";
    }

    //    UPDATE USER-INFO
    @PostMapping("/new-user-info")
    public @ResponseBody ResponseEntity<GenericResponse> putRecruiterInfo(@RequestBody @Valid User2Dto user2Dto,
                                                                          Authentication authentication,
                                                                          HttpStatus httpStatus) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean emailConfirmed = homeService.confirmEmailAndSave(userDetails.getUsername(), user2Dto);
        if (emailConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The recruiter's information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The updating user not found.");
    }
}
