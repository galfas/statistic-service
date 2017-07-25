package com.mjs.statistic.service.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class BaseController {

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIlegalArgumentException() {
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleException() {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
