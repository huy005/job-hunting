package com.iiproject.jobsearch.services;

import com.iiproject.jobsearch.dto.GenericResponse;
import com.iiproject.jobsearch.dto.User2Dto;
import com.iiproject.jobsearch.dto.UserDto;
import com.iiproject.jobsearch.entities.User;
import com.iiproject.jobsearch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    private final UserRepository userRepository;

    public CompanyServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//    @Override
//    public List<Recruitment> getRecruitmentByQuantity() {
//        return List.of();
//    }

    public User findByEmail(String email) {
        Optional<User> recruiter = userRepository.findByEmail(email);
        User theRecruiter = null;
        if (recruiter.isPresent()) theRecruiter = recruiter.get();
        return theRecruiter;
    }

    public User getUserByUserId(int id) {
        Optional<User> recruiter = userRepository.findByUserId(id);
        User theRecruiter = null;
        if (recruiter.isPresent()) theRecruiter = recruiter.get();
        return theRecruiter;
    }

    @Override
    public void getUserWithId(UserDto theUserDto) {
       User recruiter = getUserByUserId(theUserDto.getUserId());
       if (recruiter != null) {
           recruiter.setEmail(theUserDto.getEmail());
           recruiter.setUsername(theUserDto.getUsername());
           recruiter.setUserAddress(theUserDto.getUserAddress());
           recruiter.setPhoneNumber(theUserDto.getUserPhoneNumber());
           recruiter.setUserDescription(theUserDto.getUserDescription());
           userRepository.save(recruiter);
       }
    }

    @Override
    public boolean confirmEmailAndSave(String email, User2Dto theUserDto) {
        User theUser = findByEmail(email);
        if (theUser != null) {
            theUser.setEmail(theUserDto.getEmail());
            theUser.setUsername(theUserDto.getUsername());
            theUser.setUserAddress(theUserDto.getUserAddress());
            theUser.setPhoneNumber(theUserDto.getUserPhoneNumber());
            userRepository.save(theUser);
            return true;
        }
        return false;
    }
}
