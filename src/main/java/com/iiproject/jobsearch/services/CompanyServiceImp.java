package com.iiproject.jobsearch.services;

import com.iiproject.jobsearch.dto.CompanyDto;
import com.iiproject.jobsearch.dto.GenericResponse;
import com.iiproject.jobsearch.dto.User2Dto;
import com.iiproject.jobsearch.dto.UserDto;
import com.iiproject.jobsearch.entities.Company;
import com.iiproject.jobsearch.entities.User;
import com.iiproject.jobsearch.repositories.CompanyRepository;
import com.iiproject.jobsearch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
public class CompanyServiceImp implements CompanyService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImp(UserRepository userRepository,
                             CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
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

//    public User getUserByUserId(int id) {
//        Optional<User> recruiter = userRepository.findByUserId(id);
//        User theRecruiter = null;
//        if (recruiter.isPresent()) theRecruiter = recruiter.get();
//        return theRecruiter;
//    }

//    @Override
//    public void getUserWithId(UserDto theUserDto) {
//       User recruiter = getUserByUserId(theUserDto.getUserId());
//       if (recruiter != null) {
//           recruiter.setEmail(theUserDto.getEmail());
//           recruiter.setUsername(theUserDto.getUsername());
//           recruiter.setUserAddress(theUserDto.getUserAddress());
//           recruiter.setPhoneNumber(theUserDto.getUserPhoneNumber());
//           recruiter.setUserDescription(theUserDto.getUserDescription());
//           userRepository.save(recruiter);
//       }
//    }

    @Override
    public boolean confirmEmailAndSave(String email, User2Dto user2Dto) {
        User theUser = findByEmail(email);
        if (theUser != null) {
            theUser.setEmail(user2Dto.getEmail());
            theUser.setUsername(user2Dto.getUsername());
            theUser.setUserAddress(user2Dto.getUserAddress());
            theUser.setPhoneNumber(user2Dto.getUserPhoneNumber());
            theUser.setUserDescription(user2Dto.getUserDescription());
            userRepository.save(theUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean confirmCompanyAndSave(String email, CompanyDto companyDto) {
        User theUser = findByEmail(email);
        if (theUser != null) {
            Company confirmedCompany = theUser.getCompany();
            confirmedCompany.setCompanyEmail(companyDto.getCompanyEmail());
            confirmedCompany.setCompanyName(companyDto.getCompanyName());
            confirmedCompany.setCompanyAddress(companyDto.getCompanyAddress());
            confirmedCompany.setCompanyPhoneNumber(companyDto.getCompanyPhoneNumber());
            confirmedCompany.setCompanyDescription(companyDto.getCompanyDescription());
            companyRepository.save(confirmedCompany);
            return true;
        }
        return false;
    }
}
