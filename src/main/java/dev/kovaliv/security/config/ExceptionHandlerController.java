package dev.kovaliv.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Level;

import static org.springframework.http.HttpStatus.*;

@Log
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        log.log(Level.WARNING, e.getLocalizedMessage(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
    }
}
