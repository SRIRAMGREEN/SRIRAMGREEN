package com.timesheet.entity.registration.controller;

import com.timesheet.entity.registration.entity.User;
import com.timesheet.entity.registration.repo.UserRepo;
import com.timesheet.entity.registration.service.UserRegistrationService;
import com.timesheet.entity.registration.serviceimpl.UserRegistrationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    UserRegistrationServiceImpl userRegistrationService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/createAccount")
    public User register(@RequestBody User user, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        userRegistrationService.signUpUser(user, getSiteURL(request));
        return user;
    }
    private String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"http://localhost:3000/verify");
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyCandidate(@RequestBody User user1) {
        if (!user1.getVerificationCode().isEmpty()) {
            return ResponseEntity.ok(userRegistrationService.verifyUser(user1));
//                    return "/createPassword";
        }
        return (ResponseEntity<?>) ResponseEntity.notFound();
        //        return "/register";
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) throws Exception {
        String tempLoginId = user.getLoginId();
        String tempPass = user.getPassword();
        User userObj = null;
        if(tempLoginId != null && tempPass != null){
            userObj = userRegistrationService.login(tempLoginId,tempPass);
        }
        if(userObj == null){
            throw new Exception("invalid credentials");
        }
        return userObj;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {

        return "redirect:/login";
    }

    @PutMapping("/forgotPassword")
    public User forgotPassword(@RequestBody User user) {
        return userRegistrationService.setPassword(user);
        //        return "/login";

    }
}
