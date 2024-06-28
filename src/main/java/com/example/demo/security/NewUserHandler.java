package com.example.demo.security;

import com.example.demo.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class NewUserHandler implements Command<LoginRequest,LoginResponse> {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private LoginHandler loginHandler;

    @Override
    public ResponseEntity<LoginResponse> execute(LoginRequest request) {
        CustomUser user = new CustomUser();
        user.setUserName(request.getUserName());
        user.setPassword(encoder.encode(request.getPassword()));
        customUserRepository.save(user);
        return loginHandler.execute(request);
    }
}
