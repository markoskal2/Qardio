package com.qardio.demo.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends CustomException {
    public DataNotFoundException() {
        super("There is no record", HttpStatus.OK);
    }
}
