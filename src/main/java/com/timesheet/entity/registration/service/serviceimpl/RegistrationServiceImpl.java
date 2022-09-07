package com.timesheet.entity.registration.service.serviceimpl;


import com.timesheet.entity.registration.entity.dto.RegistrationDto;
import com.timesheet.entity.service.UserService;
import com.timesheet.entity.utils.exceptions.ServiceException;
import com.timesheet.entity.registration.entity.Registration;
import com.timesheet.entity.registration.repo.RegistrationRepo;
import com.timesheet.entity.registration.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.timesheet.entity.utils.TimeSheetErrorCodes.*;
import static com.timesheet.entity.utils.TimesheetConstants.EMAIL_REGEX;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    JavaMailSender javaMailSender;
    Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    @Autowired
    RegistrationRepo registrationRepo;


    @Override
    public RegistrationDto insertDetails(Registration registration, String siteURL) throws MessagingException, UnsupportedEncodingException {
        Registration existingUser = registrationRepo.findByEmail(registration.getEmail());
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

//        RegistrationDto registrationDto = modelMapper.map(registration, RegistrationDto.class);

        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = null;
        try {
            emailMatcher = emailPattern.matcher(registration.getEmail());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid");
        }
        boolean validEmail = emailMatcher.find();
        Registration registration1 = null;
        if (validEmail) {
//            Registration userExists = registrationRepo.findByEmail(registration.getEmail());
            if (Objects.nonNull(existingUser)) {
                throw new ServiceException(MAILID_ALREADY_PRESENT.getErrorCode(), "Candidate Details already exists");
            }
            registration1 = sendRegistrationConfirmationEmail(registration, siteURL);

//            RegistrationDto registrationDto = modelMapper.map(registration,RegistrationDto.class);
        }
        RegistrationDto registrationDto = modelMapper.map(registration,RegistrationDto.class);
        if (Objects.nonNull(registration1)) {
            try {
                createUser(registration1);
                return registrationDto;
            } catch (Exception e) {
                throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid Data");
            }
        }
        return registrationDto;
    }
    @Override
    public Registration sendRegistrationConfirmationEmail(Registration registration, String siteURL) throws MessagingException, UnsupportedEncodingException {
        Registration register = registrationRepo.findByEmail(registration.getEmail());
        String code = getRandom();
        registration.setVerificationCode(code);
        registrationRepo.findByVerificationCode(registration.getVerificationCode());

        String toAddress = registration.getEmail();
        String verify = "<h3><a href=\"http://localhost:3000/verify\"+ randomString '>Verify</a></h3>";

        String fromAddress = "timesheet027@gmail.com";
        String senderName = "timesheet";
        String subject = "please verify your registration";
        String content = "Please click the link below to verify your registration:<br>"
                + verify;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", registration.getEmployeeName());
        String verifyURL = "http://localhost:3000" + "/verify?code=" + registration.getVerificationCode();
        content = content.replace("userverifyUrl", verifyURL);
//        String verifyURL = siteURL + "/verify?code=" + registration.getVerificationCode();
//        content = content.replace("http://localhost:3000/verify", verifyURL);
        // content = content.replace(baseURL, verifyURL);
        helper.setText(content, true);
        javaMailSender.send(message);
        System.out.println("Mail Send .....");
        //userRepository.save(user);
        return registrationRepo.saveAndFlush(registration);  //today changes
    }

    @Override
    public String getRandom() {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 27;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alpha.length());
            char randomChar = alpha.charAt(index);
            sb.append(randomChar);
        }
        String randomString = sb.toString();

        return randomString;
    }

    @Override
    public RegistrationDto verifyUser(Registration registration) {
        Registration verificationCode = registrationRepo.findByVerificationCode(registration.getVerificationCode());
        if (ObjectUtils.isEmpty(verificationCode)) {
            throw new ServiceException(INVALID_REGISTRATION_LINK.getErrorCode(), INVALID_REGISTRATION_LINK.getErrorDesc());
        } else {
            System.out.println("It is working.....");
            verificationCode.setPassword(registration.getPassword());
           registration = registrationRepo.saveAndFlush(verificationCode);
           RegistrationDto registrationDto = modelMapper.map(registration,RegistrationDto.class);
            logger.info("RegistrationServiceImpl || verifyRegistrationForUser|| Account has been verified");
            return registrationDto;
        }

    }

    @Override
    public RegistrationDto login(String loginId, String password) {
        Registration registration = null;
        RegistrationDto registrationDto;
        try {
            registration = registrationRepo.findByLoginIdAndPassword(loginId, password);
            registrationDto = modelMapper.map(registration, RegistrationDto.class);
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode());
        }
        return registrationDto;

    }

    private void createUser(Registration registration) {
        try {
            if (registration.getRoles().getRolesId() == 2) {
                //create a user entry
                userService.addEntryToUser(registration);
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        }
    }

    @Override
    public RegistrationDto setPassword(Registration registration) {
        Registration verificationCode = registrationRepo.findByEmail(registration.getEmail());
        try {
            if (ObjectUtils.isEmpty(verificationCode)) {
                throw new RuntimeException("verificationCode is empty");
            } else {
                verificationCode.setPassword(registration.getPassword());
                registrationRepo.save(verificationCode);
                RegistrationDto registrationDto = modelMapper.map(registration,RegistrationDto.class);
                logger.info("RegistrationServiceImpl || setPasswordForOwner || Password has been successfully created");
                return registrationDto;
            }
        } catch (RuntimeException e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode());
        }

    }
}


