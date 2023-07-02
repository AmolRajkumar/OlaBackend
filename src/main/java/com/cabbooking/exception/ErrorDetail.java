package com.cabbooking.exception;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ErrorDetail {

    private String error;

    private String details;

    private LocalDateTime timestamp;


}
