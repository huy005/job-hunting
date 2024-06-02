package iiproject.jobhunting.controllers;

import iiproject.jobhunting.dto.*;
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
            User user = homeService.findUserByEmail(authentication);
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
        User user = homeService.findUserByEmail(authentication);
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

//    @PostMapping("/company-jd-info")
//    public @ResponseBody ResponseEntity<GenericResponse> searchCompanyJd(@RequestBody CompanyJdDto companyJdDto,
////                                                                         Model theModel,
//                                                                         HttpStatus httpStatus) {
//        List<CompanyJdDto> companyJdDtos = homeService.searchCompanyJd(companyJdDto.getKeywords());
////        theModel.addAttribute("companyJdDtos", companyJdDtos);
//        if (companyJdDtos.isEmpty()) {
//            throw new UserNotFoundException("There is no any keyword found.");
//        }
//        return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
//                companyJdDtos, Utils.getTimeStampHelper()));
//    }

    //    UPDATE USER-INFO
    @PostMapping("/new-user-info")
    public @ResponseBody ResponseEntity<GenericResponse> putRecruiterInfo(@RequestBody @Valid User2Dto user2Dto,
                                                                          Authentication authentication,
                                                                          HttpStatus httpStatus) {
        boolean emailConfirmed = homeService.confirmEmailAndSave(authentication, user2Dto);
        if (emailConfirmed) {
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "The recruiter's information updated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UserNotFoundException("The updating user not found.");
    }

    //    OTP Authentication
    @GetMapping("/otp-authentication")
    public String getOtpAuthenticationPage(Authentication authentication) {
        boolean isUserExisted = homeService.sendOtpAuthentication(authentication);
        if (isUserExisted) {
            return "verification/otp-authentication-page";
        }
        throw new UsernameNotFoundException("The user not found.");
    }

    @PostMapping("/otp-verification")
    public @ResponseBody ResponseEntity<GenericResponse> verifyOtp(@RequestBody UserDto userDto,
                                                                   Authentication authentication,
                                                                   HttpStatus httpStatus) {
        if (homeService.verifyOtp(userDto, authentication)){
            return ResponseEntity.ok(new GenericResponse(httpStatus.OK.value(),
                    "Your account authenticated successfully.", Utils.getTimeStampHelper()));
        }
        throw new UsernameNotFoundException("The OPT code invalid.");
    }
}
