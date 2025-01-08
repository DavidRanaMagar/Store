package com.example.store.request.user;

import lombok.Data;

import java.util.Optional;

@Data
public class AddUserRequest {
    private String username;
    private String password;
    private Integer createdBy;
    private Integer roleId;
}
