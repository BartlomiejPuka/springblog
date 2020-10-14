package com.programming.springblog.service;

import com.programming.springblog.dto.RegisterRequest;
import com.programming.springblog.model.User;
import com.programming.springblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void signUp(RegisterRequest registerRequest){
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);
    }
}
