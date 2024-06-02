package iiproject.jobhunting.services;

import iiproject.jobhunting.dto.*;
import iiproject.jobhunting.entities.*;
import iiproject.jobhunting.helpers.Utils;
import iiproject.jobhunting.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Service
public class HomeServiceImp implements HomeService {

    private final JobDescriptionRepository jobDescriptionRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CvRepository cvRepository;
    private final JavaMailSender javaMailSender;

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

    private User findByEmail(String email) {
        Optional<User> recruiter = userRepository.findByEmail(email);
        User theUser = null;
        if (recruiter.isPresent()) theUser = recruiter.get();
        return theUser;
    }

    public void sendMail(String subject, String mailText) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("test-email@gmail.com");
        msg.setTo("huybqfx18856@funix.edu.vn");
        msg.setSubject(subject);
        msg.setText(mailText);
        javaMailSender.send(msg);
    }

    //UPDATE USER'S INFO
    @Override
    public boolean confirmEmailAndSave(Authentication authentication, User2Dto user2Dto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User theUser = findByEmail(userDetails.getUsername());
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
        if (recruiter.isPresent()) {
            theUser = recruiter.get();
            theUser.setVerificationStatus(1);
            userRepository.save(theUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendOtpAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(userDetails.getUsername());
        if (user != null && user.getDeleteStatus() == 0) {
            SecureRandom rand = new SecureRandom();
            String otpCode = Integer.toString(rand.nextInt(999999));
            user.setOneTimePassword(otpCode);
            user.setOtpExpiryTime(Utils.getLocalDateTimeOfNow().plusMinutes(5));
            userRepository.save(user);

//            Sending Mail
            sendMail("The Otp Code For Logging in.",
                    "Hi " + user.getUsername() + ", input \"" +
                            otpCode + "\" for the authentication. \n");
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyOtp(UserDto userDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(userDetails.getUsername());
        if (user != null && user.getDeleteStatus() == 0) {
            if (user.getOneTimePassword().equals(userDto.getOtp())) {
                return true;
            }
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
    public boolean addNewUser(UserDto theUserDto) {
//        Adding the new user
        User theUser = new User();
        theUser.setEmail(theUserDto.getEmail());
        theUser.setUsername(theUserDto.getUsername());
        theUser.setPassword("{noop}" + theUserDto.getPassword());
        theUser.setCreatedAt(Utils.getLocalDateTimeOfNow());
        theUser.setDeleteStatus(0);
        theUser.setToken(theUserDto.getToken());
        theUser.setTokenExpiryTime(Utils.getLocalDateTimeOfNow().plusHours(8));
        theUser.setVerificationStatus(0);
        int roleId = parseInt(theUserDto.getRole());
        Role role = new Role(roleId, roleId == 1 ? "RECRUITER" : "CANDIDATE");
        theUser.setRole(role);
        Company company = new Company();
        theUser.setCompany(company);
        userRepository.save(theUser);

        //            Sending to email
        sendMail("Complete Password Reset!",
                "To complete the password reset process, please click here: " +
                        "http://localhost:8080/verification-user?token=" + theUserDto.getToken());
        return true;
    }

    @Override
    public User findUserByEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = findByEmail(userDetails.getUsername());
        return user;
    }

    @Override
    public List<CompanyJdDto> searchCompanyJd(String keyword) {
        List<CompanyJdDto> companyJds = jobDescriptionRepository.searchCompanyJd(keyword);
        return companyJds;
    }


}
