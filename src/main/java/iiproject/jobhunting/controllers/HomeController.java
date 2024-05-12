package iiproject.jobhunting.controllers;

import iiproject.jobhunting.dto.UserDto;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import iiproject.jobhunting.services.HomeService;
import iiproject.jobhunting.entities.Role;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @GetMapping("/log-in-page")
    public String showLogInPage(Model theModel) {
        theModel.addAttribute("userDtoLogIn", new UserDto());
        return "log-in";
    }

    @GetMapping("/")
    public String showMainHomePage1(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = homeService.findUserByEmail(userDetails.getUsername());
        if (user != null) {
            List<JobDescription> jobDescriptionQuantities = homeService.getJobDescriptionsByQuantity();
            model.addAttribute("jobDescriptionQuantities", jobDescriptionQuantities);
            model.addAttribute("user", user);
            model.addAttribute("company", user.getCompany());
            return "home";
        }
        List<JobDescription> jobDescriptionQuantities = homeService.getJobDescriptionsByQuantity();
        model.addAttribute("jobDescriptionQuantities", jobDescriptionQuantities);
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
            throw new UsernameNotFoundException("User not found.");
        }
        return "home";
    }

    //    ADD NEW USERS
    @PostMapping("/user-registration")
    public @ResponseBody ResponseEntity<Boolean> saveUser(@RequestBody @Valid UserDto theUserDto) {
        User theUser = new User();
        theUser.setEmail(theUserDto.getEmail());
        theUser.setUsername(theUserDto.getUsername());
        theUser.setPassword(theUserDto.getPassword());
        int roleId = parseInt(theUserDto.getRole());
        Role role = new Role(roleId, roleId == 1 ? "RECRUITER" : "CANDIDATE");
        theUser.setRole(role);
        homeService.saveUserAndRole(theUser, role);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
