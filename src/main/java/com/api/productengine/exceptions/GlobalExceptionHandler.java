package com.api.productengine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcepcionProductoNoEncontrado.class)
    public ResponseEntity<Map<String, String>> manejarProductoNoEncontrado(ExcepcionProductoNoEncontrado e){
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", e.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExcepcionReglaNegocio.class)
    public ResponseEntity<Map<String, String>> menajarReglaNegocio(ExcepcionReglaNegocio e){
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", e.getMessage());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }
}
