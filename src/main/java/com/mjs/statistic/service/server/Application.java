package com.mjs.statistic.service.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mjs.statistic.service")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
