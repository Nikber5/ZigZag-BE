package com.zigzag.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidBidException extends Exception {
    public InvalidBidException(String message) {
        super(message);
    }
}
