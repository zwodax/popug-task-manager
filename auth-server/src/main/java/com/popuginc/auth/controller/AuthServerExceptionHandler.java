package com.popuginc.auth.controller;

import com.popuginc.auth.exception.UserNotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class AuthServerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public GenericWebException handleUserNotFoundException(ConstraintViolationException e) {
    return new GenericWebException(e.getCause().getMessage());
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public GenericWebException handleUserNotFoundException(UserNotFoundException e) {
    return new GenericWebException(String.format("User not found: userId=[%s]",
        e.getUserId().toString()));
  }

  @Override
  @SuppressWarnings("all")
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity.of(Optional.of(new GenericWebException(ex.getMessage())));
  }

  @Data
  @AllArgsConstructor
  public static class GenericWebException {

    private final String message;
  }


}
