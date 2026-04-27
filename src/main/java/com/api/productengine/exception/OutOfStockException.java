package com.api.productengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST , reason = "Out of stock")
public class OutOfStockException extends RuntimeException{
}
