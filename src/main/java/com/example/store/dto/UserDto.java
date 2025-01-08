package com.example.store.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer userId;
    private String username;
    private RoleDto role;
}
