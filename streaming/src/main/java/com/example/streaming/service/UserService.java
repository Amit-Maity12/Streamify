package com.example.streaming.service;

import com.example.streaming.entity.User;
import com.example.streaming.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository,PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    public User register(User user){
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public User login(String email,String password){
        User user = usersRepository.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid Email"));
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Password");
        }
        return user;
    }

    public User findById(Long id){
        return usersRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot find user"));
    }

}
