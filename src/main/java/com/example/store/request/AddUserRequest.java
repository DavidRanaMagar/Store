package com.example.store.request;

import com.example.store.model.Role;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddUserRequest {
    private int userId;
    private String username;
    private String password;
    private Integer createdBy;
    private Integer updatedBy;
    private Role role;
}
