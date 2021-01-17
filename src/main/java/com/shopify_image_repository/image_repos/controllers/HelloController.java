package com.shopify_image_repository.image_repos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping(value = "/hello",
    produces = "application/json")
    public ResponseEntity<?> sayHello()
    {
        String hello = "Welcome to the PicVault Image repository";
        return new ResponseEntity<>(hello, HttpStatus.I_AM_A_TEAPOT);
    }
}
