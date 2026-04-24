package com.api.productengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The amount is negative")
public class InvalidAmountException extends RuntimeException{
}
