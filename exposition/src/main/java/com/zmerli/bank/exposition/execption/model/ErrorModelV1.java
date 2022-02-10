package com.zmerli.bank.exposition.execption.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ErrorModelV1 {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;

    public ErrorModelV1(int statusCode, LocalDateTime timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

}
