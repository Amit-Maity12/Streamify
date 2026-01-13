package com.example.streaming.controller;

import com.example.streaming.dtos.LoginReqDTO;
import com.example.streaming.dtos.LoginResDTO;
import com.example.streaming.dtos.RegisterReqDTO;
import com.example.streaming.dtos.UserResponseDTO;
import com.example.streaming.entity.User;
import com.example.streaming.service.UserService;
import com.example.streaming.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService,JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReqDTO registerReqDTO){
       User user = new User();
       user.setUsername(registerReqDTO.getUsername());
       user.setEmail(registerReqDTO.getEmail());
       user.setPassword(registerReqDTO.getPassword());

       User savedUser = userService.register(user);

        UserResponseDTO response = new UserResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO loginReqDTO){
        User loginUser = userService.login(loginReqDTO.getEmail(),loginReqDTO.getPassword());
        String token = jwtUtil.generateToken(loginUser.getEmail());
        return ResponseEntity.ok(Map.of("token",token));
    }
}
