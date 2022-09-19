package com.timesheet.module.exceptionhandler;

import com.timesheet.module.utils.TimeSheetErrorCodes;
import com.timesheet.module.utils.exceptions.ControllerException;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex,
             HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> errorMap = new HashMap<>();
        List<String> errorList = ex.getBindingResult().
                getFieldErrors().stream().
                map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        errorMap.put("errors", errorList);
        errorMap.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(errorMap, status);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ControllerException> handleServiceException(ServiceException e) {
        ControllerException controllerException = new ControllerException(e.getErrorCode(), e.getErrorDesc());
        if (controllerException.getErrorCode().equalsIgnoreCase(TimeSheetErrorCodes.DATA_NOT_SAVED.getErrorCode())) {
            return new ResponseEntity<>(controllerException, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (controllerException.getErrorCode().equalsIgnoreCase(TimeSheetErrorCodes.DATA_NOT_FOUND.getErrorCode()) ||
                controllerException.getErrorCode().equalsIgnoreCase(TimeSheetErrorCodes.INVALID_REGISTRATION_LINK.getErrorCode())) {
            return new ResponseEntity<>(controllerException, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(controllerException, HttpStatus.BAD_REQUEST);
        }
    }

}
