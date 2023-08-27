//package com.iiproject.jobsearch.controllers;
//
//import com.iiproject.jobsearch.services.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/candidates")
//public class CandidateController {
//    HttpSession httpSession;
//
//    private UserService userService;
//
//    @Autowired
//    public CandidateController(HttpSession httpSession, UserService userService) {
//        this.httpSession = httpSession;
//        this.userService = userService;
//    }
//
//    @GetMapping("/candidatePage")
//    public String showCandidatePage(Model themodel) {
//        return "company-page";
//    }
//
//}
