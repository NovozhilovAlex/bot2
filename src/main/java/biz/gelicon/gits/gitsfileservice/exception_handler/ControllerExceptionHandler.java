package biz.gelicon.gits.gitsfileservice.exception_handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleException(ExpiredJwtException e) {
        log.warn("ExpiredJwtException, message: " + e.getMessage());
        return new ResponseEntity<>("The download link has expired", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(SignatureException e) {
        log.warn("SignatureException, message: " + e.getMessage());
        return new ResponseEntity<>("Token is not valid", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(MalformedJwtException e) {
        log.warn("MalformedJwtException, message: " + e.getMessage());
        return new ResponseEntity<>("Token is incorrect", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(FileNotFoundException e) {
        log.warn("FileNotFoundException, message: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IOException e) {
        log.warn("IOException, message: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
