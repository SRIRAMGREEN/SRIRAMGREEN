package com.timesheet.entity.serviceimpl;

import com.timesheet.entity.model.SuperAdmin;
import com.timesheet.entity.repository.SuperAdminRepo;
import com.timesheet.entity.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.timesheet.entity.utils.TimesheetConstants.EMAIL_REGEX;
import static com.timesheet.entity.utils.TimesheetConstants.MAILID_ALREADY_PRESENT;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {
    @Autowired
    SuperAdminRepo superAdminRepo;

    @Override
    public SuperAdmin signUpUser(SuperAdmin superAdmin) throws MessagingException, UnsupportedEncodingException {


        SuperAdmin constantsUser = superAdminRepo.findByEmail(superAdmin.getEmail());
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(superAdmin.getEmail());


        boolean validEmail = emailMatcher.find();
        if (validEmail) {
            SuperAdmin userExists = superAdminRepo
                    .findByEmail(superAdmin.getEmail());

            if (!Objects.isNull(userExists)) {
                throw new IllegalStateException(MAILID_ALREADY_PRESENT);

            }

        }
        return superAdmin;
    }

}
