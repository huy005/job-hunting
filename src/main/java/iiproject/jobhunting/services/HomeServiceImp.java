package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.IJobDescriptionCount;
import iiproject.jobhunting.dto.UserDto;
import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.Role;
import iiproject.jobhunting.entities.User;
import iiproject.jobhunting.repositories.CompanyRepository;
import iiproject.jobhunting.repositories.JobDescriptionRepository;
import iiproject.jobhunting.repositories.RoleRepository;
import iiproject.jobhunting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HomeServiceImp implements HomeService {

    private JobDescriptionRepository jobDescriptionRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public HomeServiceImp(JobDescriptionRepository jobDescriptionRepository,
                          UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    //    TO GET THE POPULAR COMPANIES, RECRUITMENTS, CATEGORIES By JobDescription (id,quantity)
    @Override
    public List<JobDescription> getJobDescriptionsByQuantity() {
        List<IJobDescriptionCount> jobDescriptionCounts = jobDescriptionRepository.getJobDescriptionQuantityByIdNative();
        Optional<JobDescription> jobDescriptionResult = null;
        JobDescription jobDescription = null;
        int theJobDescriptionId = 0;
        List<JobDescription> theJobDescription = new ArrayList<>();
        for (IJobDescriptionCount jobDescriptionCount : jobDescriptionCounts) {
            theJobDescriptionId = jobDescriptionCount.getJobDescriptionId();
            jobDescriptionResult = jobDescriptionRepository.findById(theJobDescriptionId);
            if (jobDescriptionResult.isPresent()) {
                jobDescription = jobDescriptionResult.get();
                theJobDescription.add(jobDescription);
            } else {
                throw new RuntimeException("Did not find donation id - " + theJobDescriptionId);
            }
        }
        return theJobDescription;
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
    public User findUserByEmail(String theEmail) {
        Optional<User> userOptional = userRepository.findByEmail(theEmail);
        User user = null;
        if (userOptional.isPresent()) {
            return user = userOptional.get();
        }
        return null;
    }


}
