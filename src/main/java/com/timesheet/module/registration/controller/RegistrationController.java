package com.timesheet.module.registration.controller;

import com.timesheet.module.registration.entity.Registration;
import com.timesheet.module.registration.dto.ChangePasswordDto;
import com.timesheet.module.registration.service.TimesheetRegistrationService;
import com.timesheet.module.utils.TimeSheetErrorCodes;
import com.timesheet.module.utils.exceptions.ControllerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    private TimesheetRegistrationService registrationService;

    @PostMapping(value = "/createAccount")
    public ResponseEntity<Registration > insertDetails(@RequestBody Registration registration) {
        return new ResponseEntity<>(registrationService.insertDetails(registration), HttpStatus.OK);

    }

    @PostMapping(value = "/loginScreen")
    public ResponseEntity<Registration> getLoginDetails(@RequestBody Registration registration) {
        return new ResponseEntity(registrationService.getLoginDetails(registration), HttpStatus.OK);
    }

    @PostMapping(value = "/verifyEmployeeAndProjectManager")
    public ResponseEntity<?> verifyCandidate(@RequestBody Registration registration) {
        if (!registration.getToken().isEmpty()) {
            Registration verificationResponse = registrationService.verifyRegistration(registration);
            return ResponseEntity.ok(verificationResponse);
        }
        ControllerException controllerExceptionHandler = new ControllerException(TimeSheetErrorCodes.PARAM_NOT_PROVIDED.getErrorCode(), TimeSheetErrorCodes.PARAM_NOT_PROVIDED.getErrorDesc());
        return new ResponseEntity<>(controllerExceptionHandler, HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/verifyForgotPassword")
    public ResponseEntity<?> verifyForgotPass(@RequestBody ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.getForgotPassToken().isEmpty() && !changePasswordDto.getNewPassword().isEmpty()) {
            return ResponseEntity.ok(registrationService.verifyForgottenPassword(changePasswordDto));
        }
        ControllerException controllerExceptionHandler = new ControllerException(TimeSheetErrorCodes.PARAM_NOT_PROVIDED.getErrorCode()
                , TimeSheetErrorCodes.PARAM_NOT_PROVIDED.getErrorDesc());
        return new ResponseEntity<>(controllerExceptionHandler, HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return new ResponseEntity<Boolean>(registrationService.changePassword(changePasswordDto), HttpStatus.OK);
    }

    @GetMapping(value = "/forgotPassword")
    public ResponseEntity<Boolean> setForgotPassword(@RequestParam String emailId) {
        return new ResponseEntity<Boolean>(registrationService.forgotPassword(emailId), HttpStatus.OK);
    }


}
