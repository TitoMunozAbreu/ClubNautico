package com.app.clubnautico.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiException {
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "ddMMyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private String path;


    public ApiException(){
        this.timestamp = LocalDateTime.now();
    }

    public ApiException(HttpStatus httpStatus){
        this();
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String message, String path) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.path = path;
    }

    public ApiException(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApiException(HttpStatus httpStatus, LocalDateTime timestamp, String message) {
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.message = message;
    }

}
