//package com.iiproject.jobsearch.controllers;
//
//import com.iiproject.jobsearch.dto.UserDto;
//import com.iiproject.jobsearch.entities.Recruitment;
//import com.iiproject.jobsearch.entities.User;
//import com.iiproject.jobsearch.services.CompanyService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/recruiters")
//public class CompanyController {
//    HttpSession httpSession;
//
//    private CompanyService companyService;
//
//    @Autowired
//    public CompanyController(HttpSession httpSession, CompanyService companyService) {
//        this.httpSession = httpSession;
//        this.companyService = companyService;
//    }
//
//    @GetMapping("/companyPage")
//    public String showCompanyPage(Model themodel) {
//        return "company-page";
//    }
//
//}
