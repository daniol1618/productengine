package com.api.productengine.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyErrorResponse {
    private int statusCode;
    private String message;

    public MyErrorResponse(String message){
        super();
        this.message = message;
    }
}
