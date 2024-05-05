package com.iiproject.jobsearch.services;

import com.iiproject.jobsearch.dto.IRecruitmentCount;
import com.iiproject.jobsearch.dto.UserDto;
import com.iiproject.jobsearch.entities.Recruitment;
import com.iiproject.jobsearch.entities.Role;
import com.iiproject.jobsearch.entities.User;
import com.iiproject.jobsearch.repositories.CompanyRepository;
import com.iiproject.jobsearch.repositories.RecruitmentRepository;
import com.iiproject.jobsearch.repositories.RoleRepository;
import com.iiproject.jobsearch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HomeServiceImp implements HomeService {

    private CompanyRepository companyRepository;
    private RecruitmentRepository recruitmentRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public HomeServiceImp(CompanyRepository companyRepository,
                          RecruitmentRepository recruitmentRepository,
                          UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.companyRepository = companyRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    //    TO GET THE POPULAR COMPANIES, RECRUITMENTS, CATEGORIES By Recruitment (id,quantity)
    @Override
    public List<Recruitment> getRecruitmentByQuantity() {
        List<IRecruitmentCount> recruitmentCounts = recruitmentRepository.getRecruitmentQuantityByIdNative();
        Optional<Recruitment> recruitmentResult = null;
        Recruitment recruitment = null;
        int theRecruitmentId = 0;
        List<Recruitment> theRecruitment = new ArrayList<>();
        for (IRecruitmentCount recruitmentCount : recruitmentCounts) {
            theRecruitmentId = recruitmentCount.getRecruitmentId();
            recruitmentResult = recruitmentRepository.findById(theRecruitmentId);
            if (recruitmentResult.isPresent()) {
                recruitment = recruitmentResult.get();
                theRecruitment.add(recruitment);
            } else {
                throw new RuntimeException("Did not find donation id - " + theRecruitmentId);
            }
        }
        return theRecruitment;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean confirmLogIn(UserDto theUserDto) {
        String userDtoEmail = theUserDto.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(userDtoEmail);
        User theUser = null;
        if (userOptional.isPresent()){
            theUser = userOptional.get();
        }else {
            throw new RuntimeException("Did not find email - " + userDtoEmail);
        }
        if(theUserDto.getPassword().equals(theUser.getPassword())){
            return true;
        }else{
            throw new RuntimeException("The password not match!!!");
        }
    }

    @Override
    public void saveUserAndRole(User theUser, Role role) {
        userRepository.save(theUser);
        roleRepository.save(role);
    }

    @Override
    public User findByID(int id) {
        Optional<User> recruiter = userRepository.findByUserId(id);
        User theRecruiter = null;
        if (recruiter.isPresent()) theRecruiter = recruiter.get();
        return theRecruiter;
    }

}
