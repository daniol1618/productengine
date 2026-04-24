package com.api.productengine.errors;

public class NoStockException extends RuntimeException{
    public NoStockException(String message){
        super(message);
    }
}
