package com.timesheet.module.registration.entity;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;

    private String forgotToken;

    public void init(Registration registration, AbstractEmailContext emailContext) {
        put("mail.smtp.starttls.enable", "true");
        put("employeeName", registration.getEmployeeName());
        setTemplateLocation("email-verification");
        setSubject("Complete your registration");
        setFrom("timesheet027@gmail.com");
        setTo(registration.getEmailId());
    }


    public void failForEmployees(Registration registration, AbstractEmailContext emailContext, List<String> failedList) {
        put("mail.smtp.starttls.enable", "true");
        put("FailedRegistration", registration.getEmailId());
        put("FailedList", failedList);
        setTemplateLocation("email-registration-failed");
        setSubject("Registration Failed For Employees!!!");
        setFrom("timesheet027@gmail.com");
        setTo(registration.getEmailId());
    }

    public void forgotPassword(Registration registration, AbstractEmailContext emailContext) {
        put("mail.smtp.starttls.enable", "true");
        put("employeeName", registration.getEmployeeName());
        setTemplateLocation("ForgetPassword");
        setSubject("Reset Your password");
        setFrom("timesheet027@gmail.com");
        setTo(registration.getEmailId());

    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void setForgotToken(String forgotToken) {
        this.forgotToken = forgotToken;
        put("forgotToken", forgotToken);
    }

    public void buildVerificationUrl(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL).queryParam("verificationToken", token).toUriString();
        put("verificationURL", url);
    }

    public void buildForgetPassUrl(final String baseURLForgotPassword, final String forgotToken) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURLForgotPassword).queryParam("forgetPasswordToken", forgotToken).toUriString();
        put("forgotPassURL", url);
    }

    public void failedMailUrl(final String baseURL) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL).queryParam("demo").toUriString();
        put("demo", url);
    }

}
