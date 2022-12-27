package com.timesheet.module.registration.service.serviceimpl;

import com.timesheet.module.Employee.repository.EmployeeRepo;
import com.timesheet.module.Employee.service.EmployeeService;
import com.timesheet.module.projectmanager.repository.ProjectManagerRepo;
import com.timesheet.module.projectmanager.service.ProjectManagerService;
import com.timesheet.module.registration.entity.AccountVerificationEmailContext;
import com.timesheet.module.registration.entity.ForgotPasswordToken;
import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.dto.ChangePasswordDto;
import com.timesheet.module.registration.repo.RegistrationRepo;
import com.timesheet.module.registration.service.ForgotPasswordService;
import com.timesheet.module.registration.service.TimesheetEmailService;
import com.timesheet.module.registration.service.TimesheetRegistrationService;
import com.timesheet.module.utils.EncryptorDecryptor;
import com.timesheet.module.utils.exceptions.ServiceException;
import com.timesheet.module.verificationmail.entity.VerificationToken;
import com.timesheet.module.verificationmail.repo.VerificationTokenRepository;
import com.timesheet.module.verificationmail.service.VerificationService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;
import static com.timesheet.module.utils.TimesheetConstants.*;

@Service
@Log4j2
public class TimesheetRegistrationServiceImpl implements TimesheetRegistrationService {

    Logger logger = LoggerFactory.getLogger(TimesheetRegistrationServiceImpl.class);
    @Autowired
    RegistrationRepo registrationRepository;
    @Autowired
    ProjectManagerService projectManagerService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    ProjectManagerRepo projectManagerRepo;

    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    TimesheetEmailService emailService;
    @Autowired
    VerificationService verificationService;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    ForgotPasswordService forgotPasswordService;

    @Value("${Timesheet.baseURL}")
    private String baseURL;
    @Value("${Timesheet.baseURLEmployees}")
    private String baseURLEmployees;
    @Value("${Timesheet.baseURLForgotPassword}")
    private String baseURLForgotPassword;

    @Override
    public Registration getLoginDetails(Registration registration) {
        Optional<Registration> savedRegistration = Optional.ofNullable(registrationRepository.findByLoginId(registration.getLoginId()));
        BCryptPasswordEncoder decoder = new BCryptPasswordEncoder();
        if (savedRegistration.isPresent()) {
            log.debug("savedRegistration>>>" + savedRegistration);
            if (!ObjectUtils.isEmpty(registration)) {
                boolean isValid = decoder.matches(registration.getPassword(), savedRegistration.get().getPassword());
                log.debug("validPassword>>>" + isValid);
                if (isValid) {
                    return savedRegistration.get();
                } else {
                    throw new ServiceException(INVALID_PASSWORD.getErrorCode(), INVALID_PASSWORD.getErrorDesc());
                }
            }
            return registration;
        } else {
            throw new ServiceException(INVALID_EMAIL_ID.getErrorCode(), INVALID_EMAIL_ID.getErrorDesc());
        }
    }

    @Transactional
    @Override
    public Registration  insertDetails(Registration registration) {
        Registration existingUser = registrationRepository.findByEmailId(registration.getEmailId());
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = null;
        try {
            emailMatcher = emailPattern.matcher(registration.getEmailId());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
        }
        boolean validEmail = emailMatcher.find();
        if (validEmail) {
            if (Objects.nonNull(existingUser)) {
                if (existingUser.isAccountStatus()) {
                    logger.error(EXISTING_CANDIDATE.getErrorDesc(), registration.getEmailId());
                    throw new ServiceException(EXISTING_CANDIDATE.getErrorCode(), EXISTING_CANDIDATE.getErrorDesc());
                } else {
                    logger.info("Sending Verification Mail again: {}", existingUser.getEmailId());
                    VerificationToken verificationToken = verificationTokenRepository.findByToken(existingUser.getToken());
                    if (Objects.nonNull(verificationToken)) {
                        verificationTokenRepository.delete(verificationToken);
                    }
                    registrationRepository.delete(existingUser);
                    Registration registration1 = sendRegistrationConfirmationEmail(registration);

                    if (Objects.nonNull(registration1)) {
                        try {
                            createProjectManagerAndEmployee(registration1);
                            return registration;
                        } catch (Exception e) {
                            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());
                        }

                    } else {

                        registration.setMailStatus(MAIL_NOT_SENT);
                        Registration savedRegistration = registrationRepository.save(registration);
                        try {
                            createProjectManagerAndEmployee(savedRegistration);
                            return registration;
                        } catch (Exception e) {
                            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());
                        }
                    }
                }
            } else {
                logger.info(SAVING_USER, registration.getEmailId());
                Registration registration1 = sendRegistrationConfirmationEmail(registration);

                if (Objects.nonNull(registration1)) {
                    try {
                        createProjectManagerAndEmployee(registration1);
                        return registration;
                    } catch (Exception e) {
                        throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());

                    }

                } else {
                    registration.setMailStatus(MAIL_NOT_SENT);
                    logger.info("RegistrationServiceImpl || insertDetails || Verification mail  is not send ");
                    try {
                        Registration savedRegistration = registrationRepository.save(registration);
                        createProjectManagerAndEmployee(savedRegistration);
                        return registration;
                    } catch (Exception e) {
                        throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());
                    }
                }
            }
        } else {
            return registration;
        }
    }

    @Override
    public Registration verifyRegistration(Registration registration) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(registration.getToken());
        if (ObjectUtils.isEmpty(verificationToken)) {
            throw new ServiceException(INVALID_REGISTRATION_LINK.getErrorCode(), INVALID_REGISTRATION_LINK.getErrorDesc());
        }
        if (!verificationToken.isExpired() && verificationToken.getExpireAt().isAfter(LocalDateTime.now())) {
            Registration registration1 = registrationRepository.findByToken(registration.getToken());
            if (registration.isAccountStatus()) {
                throw new ServiceException(USER_ALREADY_VERIFIED.getErrorCode(), USER_ALREADY_VERIFIED.getErrorDesc());
            } else {
                registration1.setAccountStatus(true);
                verificationToken.setExpired(true);
                logger.info("RegistrationServiceImpl || verifyRegistration|| verification has been expired");
                verificationTokenRepository.save(verificationToken);
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                registration1.setPassword(encoder.encode(registration.getPassword()));
                logger.info("RegistrationServiceImpl || verifyRegistration|| Account has been verified");
                return registrationRepository.saveAndFlush(registration1);
            }
        } else {
            throw new ServiceException(LINK_EXPIRED.getErrorCode(), LINK_EXPIRED.getErrorDesc());
        }
    }

    public Registration sendRegistrationConfirmationEmail(Registration registration) {
        VerificationToken verificationToken = verificationService.createVerificationToken();
        registration.setToken(verificationToken.getToken());
        try {
            registrationRepository.save(registration);
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());
        }
        verificationToken.setRegistration(registration);
        verificationTokenRepository.save(verificationToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(registration, emailContext);
        emailContext.setToken(verificationToken.getToken());

        if (registration.getRoles().getRolesId() == 1) {
            emailContext.buildVerificationUrl(baseURLEmployees, verificationToken.getToken());
        } else if (registration.getRoles().getRolesId() == 2) {
            emailContext.buildVerificationUrl(baseURLEmployees, verificationToken.getToken());
        } else {
            emailContext.buildVerificationUrl(baseURL, verificationToken.getToken());
        }
        try {
            emailService.sendMail(emailContext, verificationToken.getToken());
            registration.setMailStatus("Mail send successfully");

            logger.info("RegistrationServiceImpl || sendRegistrationConfirmationEmail || Registration Mail Sent");

            try {
                return registrationRepository.save(registration);
            } catch (Exception e) {
                throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("RegistrationServiceImpl || sendRegistrationConfirmationEmail || Unable to send email for mail id {}", registration.getEmailId());
        }
        return null;
    }


    @Transactional
    @Override
    public Registration verifyRegistrationForProjectManagerAndEmployee(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (ObjectUtils.isEmpty(verificationToken)) {
            throw new ServiceException(INVALID_REGISTRATION_LINK.getErrorCode(), INVALID_REGISTRATION_LINK.getErrorDesc());
        }
        if (!verificationToken.isExpired() && verificationToken.getExpireAt().isAfter(LocalDateTime.now())) {
            Registration registration1 = registrationRepository.findByToken(token);
            if (registration1.isAccountStatus()) {
                throw new ServiceException(USER_ALREADY_VERIFIED.getErrorCode(), USER_ALREADY_VERIFIED.getErrorDesc());
            } else {
                logger.info("RegistrationServiceImpl || verifyRegistrationForProjectManagerAndEmployee || Account has been verified");
                return registrationRepository.findByEmailId(registration1.getEmailId());
            }
        } else {
            throw new ServiceException(LINK_EXPIRED.getErrorCode(), LINK_EXPIRED.getErrorDesc());
        }
    }

    private void createProjectManagerAndEmployee(Registration registration) {
        if (registration.getRoles().getRolesId() == 1) {
            //create an ProjectManger entry
            projectManagerService.addEntryToProjectManager(registration);
        } else if (registration.getRoles().getRolesId() == 2) {
            //create an Employee entry
            employeeService.addEntryToEmployee(registration);
        }

    }

    @Override
    public boolean changePassword(ChangePasswordDto changePasswordDto) {

        try {
            Registration savedRegistration = (registrationRepository.findByEmailId(changePasswordDto.getEmailId()));

            String oldPasswordFromDB = EncryptorDecryptor.decrypt(savedRegistration.getPassword());

            if (oldPasswordFromDB.equals(changePasswordDto.getOldPassword())) {
                savedRegistration.setPassword(EncryptorDecryptor.encrypt(changePasswordDto.getNewPassword()));
                registrationRepository.saveAndFlush(savedRegistration);
                return true;
            } else {
                throw new ServiceException(INVALID_OLD_PASSWORD.getErrorCode(), INVALID_OLD_PASSWORD.getErrorDesc());
            }
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
        }
    }

    @Override
    public boolean forgotPassword(String emailId) {
        try {
            ForgotPasswordToken forgotPasswordToken = forgotPasswordService.createForgotPasswordToken();
            Registration registrationFromDb = registrationRepository.findByEmailId(emailId);
            forgotPasswordToken.setRegistration(registrationFromDb);
            forgotPasswordService.saveToken(forgotPasswordToken);
            AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
            emailContext.forgotPassword(registrationFromDb, emailContext);
            emailContext.setForgotToken(forgotPasswordToken.getForgotToken());
            emailContext.buildForgetPassUrl(baseURLForgotPassword, forgotPasswordToken.getForgotToken());
            try {
                emailService.forgotMail(emailContext, forgotPasswordToken.getForgotToken());
                logger.info("RegistrationServiceImpl || forgotPassword || Reset Password token is sent successfully");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            throw new ServiceException(INVALID_EMAIL_ID.getErrorCode(), INVALID_EMAIL_ID.getErrorDesc());
        }
    }

    @Override
    public boolean verifyForgottenPassword(ChangePasswordDto changePasswordDto) {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findByForgotToken(changePasswordDto.getForgotPassToken());
        if (ObjectUtils.isEmpty(forgotPasswordToken)) {
            throw new ServiceException(INVALID_LINK_FOR_FORGOT_PASSWORD.getErrorCode(), INVALID_LINK_FOR_FORGOT_PASSWORD.getErrorDesc());
        }
        if (!forgotPasswordToken.isExpired() && forgotPasswordToken.getExpireAt().isAfter(LocalDateTime.now())) {
            Registration registration = forgotPasswordToken.getRegistration();
            registration.setPassword(EncryptorDecryptor.encrypt(changePasswordDto.getNewPassword()));
            forgotPasswordToken.setExpired(true);
            logger.info("RegistrationServiceImpl || verifyForgottenPassword || Token set to expired");
            registrationRepository.saveAndFlush(registration);
            forgotPasswordService.saveToken(forgotPasswordToken);
            logger.info("RegistrationServiceImpl || verifyForgottenPassword || Password successfully reset");
            return true;
        } else {
            throw new ServiceException(LINK_EXPIRED.getErrorCode(), LINK_EXPIRED.getErrorDesc());
        }
    }
}
