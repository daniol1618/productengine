package com.api.productengine.errors;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String message;

    public ErrorResponse(Integer status, String message){
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
}
