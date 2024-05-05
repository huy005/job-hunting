package com.iiproject.jobsearch.controllers;

import com.iiproject.jobsearch.dto.UserDto;
import com.iiproject.jobsearch.entities.*;
import com.iiproject.jobsearch.services.HomeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/")
public class HomeController {
    HttpSession httpSession;

    private HomeService homeService;

    @Autowired
    public HomeController(HttpSession httpSession, HomeService homeService) {
        this.httpSession = httpSession;
        this.homeService = homeService;
    }

    @GetMapping("/logInPage")
    public String showLogInPage(Model theModel) {
        theModel.addAttribute("userDtoLogIn", new UserDto());
        return "log-in";
    }

    @GetMapping("/")
    public String showMainHomePage1(Model themodel) {
        List<Recruitment> recruitmentsQuantity = homeService.getRecruitmentByQuantity();
        themodel.addAttribute("recruitmentsQuantity", recruitmentsQuantity);
        return "main-home";
    }

    @GetMapping("/home")
    public String showHomePage(Model themodel) {
        List<Recruitment> recruitmentsQuantity = homeService.getRecruitmentByQuantity();
        themodel.addAttribute("recruitmentsQuantity", recruitmentsQuantity);
        return "home";
    }

    @GetMapping("/main-home")
    public String showMainHomePage2(Model theModel) {
        List<Recruitment> recruitmentsQuantity = homeService.getRecruitmentByQuantity();
        theModel.addAttribute("recruitmentsQuantity", recruitmentsQuantity);
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = homeService.findByID(userDetails.getUserId());
//        theModel.addAttribute("user", user);
        return "main-home";
    }

    @PostMapping("/showMainHomePage")
    public String showMainHomePage() {
        return "main-home";
    }

    //    ADD NEW USERS
    @PostMapping("/save")
    public @ResponseBody ResponseEntity<Boolean> saveUser(@RequestBody @Valid UserDto theUserDto) {
        User theUser = new User();
        theUser.setEmail(theUserDto.getEmail());
        theUser.setUsername(theUserDto.getUsername());
        theUser.setPassword(theUserDto.getPassword());
        int roleId = parseInt(theUserDto.getRole());
        Role role = new Role(roleId, roleId == 1 ? "Recruiter" : "Candidate");
        theUser.setRole(role);
        homeService.saveUserAndRole(theUser, role);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    //    LOGIN PROCESSING
    @PostMapping("/processingLogInForm")
    public String processLogInForm(@ModelAttribute("userDtoLogIn") UserDto theUserDto) {
        boolean logInConfirmed = homeService.confirmLogIn(theUserDto);
        if (logInConfirmed) {
            return "main-home";
        }
        // Denied Page
        return "log-in";
    }

    //    UPDATE
//    @GetMapping("/update")
//    public String update(Authentication authentication, Model theModel) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = homeService.getUserWithEmail(userDetails.getUsername());
//        theModel.addAttribute("user", user);
//        return "recruit/recruit-info";
//    }
}
