package com.example.store.dto;

import com.example.store.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private Integer userId;
    private Role role;
    private String username;
}
