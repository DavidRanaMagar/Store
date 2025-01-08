package com.example.store.request.user;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String password;
    private Integer updatedBy;
    private Integer roleId;
}
