package com.example.streaming.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReqDTO {
    private String username;
    private String email;
    private String password;

}
