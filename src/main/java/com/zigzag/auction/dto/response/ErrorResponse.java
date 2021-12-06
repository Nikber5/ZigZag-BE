package com.zigzag.auction.dto.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ErrorResponse {
    private final String timestamp;
    private final int status;
    private final List<String> errors;

    public ErrorResponse(String timestamp, int status, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
    }

    public static ErrorResponse of(RuntimeException exception, int status) {
        String timestamp = LocalDateTime.now()
                .atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        List<String> errors = List.of(exception.getMessage());
        return new ErrorResponse(timestamp, status, errors);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
