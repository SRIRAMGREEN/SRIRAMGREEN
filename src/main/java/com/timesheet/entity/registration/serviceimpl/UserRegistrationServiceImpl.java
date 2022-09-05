package com.timesheet.entity.registration.serviceimpl;

import com.timesheet.entity.registration.entity.User;
import com.timesheet.entity.registration.repo.UserRepo;
import lombok.AllArgsConstructor;
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

import static com.timesheet.entity.utils.TimesheetConstants.EMAIL_REGEX;
import static com.timesheet.entity.utils.TimesheetConstants.MAILID_ALREADY_PRESENT;

@Service
@AllArgsConstructor
public class UserRegistrationServiceImpl {
    private final JavaMailSender javaMailSender;

    @Autowired
    private UserRepo userRepository;

    public User signUpUser(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {

        User constantsUser = userRepository.findByEmail(user.getEmail());
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(user.getEmail());
        boolean validEmail = emailMatcher.find();
        if (validEmail) {
            User userExists = userRepository
                    .findByEmail(user.getEmail());

            if (!Objects.isNull(userExists)) {
//                throw new IllegalStateException("email already taken");
                throw new IllegalStateException(MAILID_ALREADY_PRESENT);

            }
            sendRegistrationConfirmationEmail(user, siteURL);
        }
        return user;
    }
    public User sendRegistrationConfirmationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        User user1 = userRepository.findByEmail(user.getEmail());
        String code = getRandom();
        user.setVerificationCode(code);
        userRepository.findByVerificationCode(user.getVerificationCode());

        String toAddress = user.getEmail();
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

        content = content.replace("[[name]]", user.getEmployeeName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        content = content.replace("http://localhost:3000/verify", verifyURL);
        // content = content.replace(baseURL, verifyURL);

        helper.setText(content, true);
        javaMailSender.send(message);

        System.out.println("Mail Send .....");
        //userRepository.save(user);
        return userRepository.save(user);
    }

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


    public User verifyUser(User user) {
        User code = userRepository.findByVerificationCode(user.getVerificationCode());

        if (ObjectUtils.isEmpty(code)) {
            System.out.println("Invalid code....");
            throw new RuntimeException("Code is empty");
        } else {
            System.out.println("It is working.....");

            code.setPassword(user.getPassword());
            userRepository.saveAndFlush(code);

            return code;
        }

    }

    public User login(String loginId, String password) {
        User user = userRepository.findByLoginIdAndPassword(loginId, password);
        return user;

    }


    public User setPassword(User user) {
        User code = userRepository.findByEmail(user.getEmail());
        if (ObjectUtils.isEmpty(code)) {
            System.out.println("Invalid mail....");
            throw new RuntimeException("Code is empty");
        } else {
            System.out.println("It is working.....");
            code.setPassword(user.getPassword());
            userRepository.save(code);
            return code;
        }

    }
}


