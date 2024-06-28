package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private NewUserHandler newUserHandler;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return loginHandler.execute(request);
    }

    @PostMapping("/createNewUser")
    public ResponseEntity<LoginResponse> createNewUser(@RequestBody LoginRequest request){
        return newUserHandler.execute(request);
    }
}
