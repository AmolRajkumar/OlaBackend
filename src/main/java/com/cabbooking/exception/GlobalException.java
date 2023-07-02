package com.cabbooking.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException ue, WebRequest request) {

        ErrorDetail error = new ErrorDetail(ue.getMessage(),
                request.getDescription(false), LocalDateTime.now());


        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DriverExcption.class)
    public ResponseEntity<ErrorDetail> driverExceptionHandler(DriverExcption de, WebRequest request) {
        ErrorDetail error = new ErrorDetail(de.getMessage(),
                request.getDescription(false), LocalDateTime.now());


        return new ResponseEntity<ErrorDetail>(error, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(RideExcption.class)
    public ResponseEntity<ErrorDetail> rideExceptionHandler(RideExcption re, WebRequest request) {
        ErrorDetail error = new ErrorDetail(re.getMessage(),
                request.getDescription(false), LocalDateTime.now());


        return new ResponseEntity<ErrorDetail>(error, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> handleValidationException(ConstraintViolationException ex) {

        StringBuilder errorMessage = new StringBuilder();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {

            errorMessage.append(violation.getMessage() + "\n");

        }

        ErrorDetail error = new ErrorDetail(errorMessage.toString(), "validation Error", LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidException(MethodArgumentNotValidException me) {

        ErrorDetail error = new ErrorDetail(me.getBindingResult().getFieldError().getField(), "validation Error", LocalDateTime.now());


        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>otherExceptionHandler(Exception ue,WebRequest request){

        ErrorDetail err=new ErrorDetail(ue.getMessage(),request.getDescription(false),LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err,HttpStatus.ACCEPTED);
    }

}
