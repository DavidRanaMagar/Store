package com.example.store.service.user;

import com.example.store.dto.UserDto;
import com.example.store.model.User;
import com.example.store.request.AddUserRequest;
import com.example.store.request.UserUpdateRequest;

import java.util.List;

public interface UserServiceInterface {
    User getUser(int id);
    User getUserByUsername(String username);
    User addUser(AddUserRequest user);
    void deleteUser(int id);
    User updateUser(UserUpdateRequest user, int userId);
    List<User> getAllUsers();
    public UserDto convertUserToDto(User user);
}
