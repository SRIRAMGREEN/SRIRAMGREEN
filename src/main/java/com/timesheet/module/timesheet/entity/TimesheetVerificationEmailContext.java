package com.timesheet.module.timesheet.entity;

import com.timesheet.module.registration.entity.Registration;
import org.springframework.web.util.UriComponentsBuilder;

public class TimesheetVerificationEmailContext extends TimesheetAbstractEmailContext {

    public void init(Registration registration) {
        put("mail.smtp.starttls.enable", "true");
        put("projectManager", registration.getEmployeeName());
        setTemplateLocation("timesheet-verify");
        setSubject("Timesheet Submitted");
        setFrom("jagadeesh.m@qbrainx.com");
        setTo(registration.getEmailId());
    }

    public void initStatusApproved(Registration registration) {
        put("mail.smtp.starttls.enable", "true");
        put("projectManager", registration.getEmployeeName());
        setTemplateLocation("timesheetStatus-verification");
        setSubject("TimesheetStatus APPROVED");
        setFrom("jagadeesh.m@qbrainx.com");
        setTo(registration.getEmailId());
    }
    public void initStatusReject(Registration registration) {
        put("mail.smtp.starttls.enable", "true");
        put("projectManager", registration.getEmployeeName());
        setTemplateLocation("timesheetStatus-verification");
        setSubject("TimesheetStatus REJECTED");
        setFrom("jagadeesh.m@qbrainx.com");
        setTo(registration.getEmailId());
    }
    public void buildVerificationUrl(final String timesheetURL, String emailId) {
        final String url = UriComponentsBuilder.fromHttpUrl(timesheetURL).queryParam("verificationToken").toUriString();
        put("verificationURL", url);
    }
}
