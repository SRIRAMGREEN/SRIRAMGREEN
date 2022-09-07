package com.timesheet.entity.registration.controller;

import com.timesheet.entity.registration.entity.dto.RegistrationDto;
import com.timesheet.entity.registration.entity.Registration;
import com.timesheet.entity.registration.service.RegistrationService;

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
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/createAccount")
    public RegistrationDto insertDetails(@RequestBody Registration registration, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        return registrationService.insertDetails(registration, getSiteURL(request));

    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "http://localhost:3000/verify");
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody Registration registration) {
        if (!registration.getVerificationCode().isEmpty()) {
            return ResponseEntity.ok(registrationService.verifyUser(registration));
//                    return "/createPassword";
        }
        return (ResponseEntity<?>) ResponseEntity.notFound();
        //        return "/register";
    }

    @PostMapping("/login")
    public RegistrationDto loginUser(@RequestBody Registration registration) throws Exception {
        String tempLoginId = registration.getLoginId();
        String tempPass = registration.getPassword();
        RegistrationDto userObj = null;
        if (tempLoginId != null && tempPass != null) {
            userObj = registrationService.login(tempLoginId, tempPass);
        }
        if (userObj == null) {
            throw new Exception("invalid credentials");
        }
        return userObj;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/login";
    }

    @PutMapping("/forgotPassword")
    public RegistrationDto forgotPassword(@RequestBody Registration registration) {
        return registrationService.setPassword(registration);
        //        return "/login";

    }
}
