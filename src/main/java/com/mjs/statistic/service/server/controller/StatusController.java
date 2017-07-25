package com.mjs.statistic.service.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController extends BaseController {

  @RequestMapping("/status")
  public ResponseEntity status() {
    return new ResponseEntity<>("ok", HttpStatus.OK);
  }
}
