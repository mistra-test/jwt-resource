package com.example.jwtresource.controller;

import com.example.jwtresource.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    @Autowired private JwtService jwtService;

    @PostMapping("/encode")
    public String encodeUser(@RequestBody User userDetails) {
        return jwtService.encodeUser(userDetails);
    }

    @GetMapping("/decode/{token}")
    public User decodeToken(@PathVariable String token) {
        return jwtService.decodeToken(token);
    }
}
