package com.timesheet.module.task.entity;

import com.timesheet.module.registration.entity.Registration;
import org.springframework.web.util.UriComponentsBuilder;



public class TaskVerificationEmailContextTask extends AbstractEmailContextTask {

    public void init(Registration registration) {
        put("mail.smtp.starttls.enable", "true");
        put("employeeName", registration.getEmployeeName());
        setTemplateLocation("task-verification");
        setSubject("Task Allocated");
        setFrom("jagadeesh.m@qbrainx.com");
        setTo(registration.getEmailId());
    }


    public void buildVerificationUrl(final String taskURL, String emailId) {
        final String url = UriComponentsBuilder.fromHttpUrl(taskURL).queryParam("verificationTask").toUriString();
        put("verificationURL", url);
    }
}
