package com.example.dinostitches.exceptions;

import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)

public class UnAuthorizedExceptions extends RuntimeException{
    private static final long serialVersionUID =1L;
    private ApiResponse apiResponse;
    private String message;
    public UnAuthorizedExceptions(ApiResponse apiResponse){
        super();
        this.apiResponse=apiResponse;
    }
    public UnAuthorizedExceptions(String message){
        super();
        this.message=message;
    }
     public UnAuthorizedExceptions(String message, Throwable cause){
        super(message, cause);
     }
}
