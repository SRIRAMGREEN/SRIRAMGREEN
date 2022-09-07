//package com.timesheet.entity.serviceimpl;
//
//
//import com.timesheet.entity.exceptions.ServiceException;
//import com.timesheet.entity.model.SuperAdmin;
//import com.timesheet.entity.registration.entity.Registration;
//import com.timesheet.entity.repository.SuperAdminRepo;
//import com.timesheet.entity.service.SuperAdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import javax.mail.MessagingException;
//import java.io.UnsupportedEncodingException;
//
//import static com.timesheet.entity.utils.TimeSheetErrorCodes.INVALID_REQUEST;
//
//@Service
//public class SuperAdminServiceImpl implements SuperAdminService {
//    @Autowired
//    SuperAdminRepo superAdminRepo;
//
//    @Override
//    public String adminLogin(String loginId, String password) throws MessagingException, UnsupportedEncodingException {
//        try {
//            return   superAdminRepo.findByLoginIdAndPassword(loginId,password);
//        } catch (Exception e) {
//            throw new ServiceException(INVALID_REQUEST.getErrorCode());
//        }
//
//    }
//}