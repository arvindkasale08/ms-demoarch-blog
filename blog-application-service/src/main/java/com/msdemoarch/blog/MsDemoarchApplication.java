package com.msdemoarch.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsDemoarchApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(MsDemoarchApplication.class);
    app.setWebApplicationType(WebApplicationType.REACTIVE);
    app.run(args);
  }
}