package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.CompanyJdDto;
import iiproject.jobhunting.dto.IJobDescriptionCount;
import iiproject.jobhunting.dto.User2Dto;
import iiproject.jobhunting.dto.UserDto;
import iiproject.jobhunting.entities.*;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class HomeServiceImp implements HomeService {

    private JobDescriptionRepository jobDescriptionRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CvRepository cvRepository;
    private JavaMailSender javaMailSender;

//    @Value("#{server.contextPath}")
//    private String contextPath;

    @Autowired
    public HomeServiceImp(JobDescriptionRepository jobDescriptionRepository,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          CvRepository cvRepository,
                          JavaMailSender javaMailSender) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cvRepository = cvRepository;
        this.javaMailSender = javaMailSender;
    }

    public User findByEmail(String email) {
        Optional<User> recruiter = userRepository.findByEmail(email);
        User theUser = null;
        if (recruiter.isPresent()) theUser = recruiter.get();
        return theUser;
    }

    //UPDATE USER'S INFO
    @Override
    public boolean confirmEmailAndSave(String email, User2Dto user2Dto) {
        User theUser = findByEmail(email);
        if (theUser != null) {
            theUser.setUsername(user2Dto.getUsername());
            theUser.setUserAddress(user2Dto.getUserAddress());
            theUser.setPhoneNumber(user2Dto.getUserPhoneNumber());
            theUser.setUserDescription(user2Dto.getUserDescription());
            if (user2Dto.getUserImageFileName().contains(".png") || user2Dto.getUserImageFileName().contains(".jpg") || user2Dto.getUserImageFileName().contains(".jpeg")) {
                theUser.setUserImage(user2Dto.getUserImageFileName());
            } else {
                Cv newCv = new Cv();
                newCv.setCvFileName(user2Dto.getCvFileName());
                newCv.setUser(theUser);
                newCv.setUpdatedAt(Utils.getLocalDateTimeOfNow());
                newCv.setDeleteStatus(0);
                cvRepository.save(newCv);
            }
            userRepository.save(theUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyUserByToken(String token) {
        Optional<User> recruiter = userRepository.getUserByToken(token);
        User theUser = null;
        if (recruiter.isPresent()){
            theUser = recruiter.get();
            theUser.setVarificationStatus(1);
            userRepository.save(theUser);
            return true;
        }
        return false;
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
        if (userOptional.isPresent()) {
            theUser = userOptional.get();
        } else {
            throw new RuntimeException("Did not find email - " + userDtoEmail);
        }
        if (theUserDto.getPassword().equals(theUser.getPassword())) {
            return true;
        } else {
            throw new RuntimeException("The password not match!!!");
        }
    }

    @Override
    public boolean addNewUser(UserDto theUserDto) {
//        Adding the new user
        User theUser = new User();
        theUser.setEmail(theUserDto.getEmail());
        theUser.setUsername(theUserDto.getUsername());
        theUser.setPassword("{noop}" + theUserDto.getPassword());
        theUser.setCreatedAt(Utils.getLocalDateTimeOfNow());
        theUser.setDeleteStatus(0);
        theUser.setToken(theUserDto.getToken());
        theUser.setTokenExpiryDate(Utils.getLocalDateTimeOfNow().plusHours(8));
        theUser.setVarificationStatus(0);
        int roleId = parseInt(theUserDto.getRole());
        Role role = new Role(roleId, roleId == 1 ? "RECRUITER" : "CANDIDATE");
        theUser.setRole(role);
        Company company = new Company();
        theUser.setCompany(company);
        userRepository.save(theUser);

        //            Sending to email
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("huybqfx18856@funix.edu.vn");
        msg.setSubject("Complete Password Reset!");
        msg.setFrom("test-email@gmail.com");
        msg.setText("To complete the password reset process, please click here: " +
                        "http://localhost:8080/verification-user?token=" + theUserDto.getToken());
        javaMailSender.send(msg);
        return true;
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

    @Override
    public List<CompanyJdDto> searchCompanyJd(String keyword) {
        List<CompanyJdDto> companyJds = jobDescriptionRepository.searchCompanyJd(keyword);
        return companyJds;
    }


}
