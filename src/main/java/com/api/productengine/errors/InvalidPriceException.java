package com.api.productengine.errors;

public class InvalidPriceException extends RuntimeException{
    public InvalidPriceException(String message){
        super(message);
    }
}
