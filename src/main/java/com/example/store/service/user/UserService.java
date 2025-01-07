package com.example.store.service.user;

import com.example.store.dto.UserDto;
import com.example.store.exceptions.UserAlreadyExistsException;
import com.example.store.exceptions.UserNotFoundException;
import com.example.store.model.User;
import com.example.store.repository.UserRepository;
import com.example.store.request.AddUserRequest;
import com.example.store.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username Not Found"));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete,
                        () -> {throw new UserNotFoundException("User Not Found");});
    }

    @Override
    public User addUser(AddUserRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .<User>map(existingUser -> {
                    throw new UserAlreadyExistsException(existingUser.getUsername());
                })
                .orElseGet(() -> userRepository.save(createUser(request)));
    }

    private User createUser(AddUserRequest request) {
        return new User(
                request.getUsername(),
                request.getPassword(),
                request.getCreatedBy(), // Both createdBy and UpdatedBy come through DTO
                request.getUpdatedBy(),
                request.getRole()
        );
    }

    @Override
    public User updateUser(UserUpdateRequest request, int userId) {
        return userRepository.findById(userId)
                .map(existingUser -> updateExistingUser(existingUser, request))
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    private User updateExistingUser(User existingUser, UserUpdateRequest request) {
        existingUser.setUsername(request.getUsername());
        existingUser.setPassword(request.getPassword());
        existingUser.setUpdatedBy(request.getUpdatedBy()); //updatedBy ID comes through DTO
        existingUser.setRole(request.getRole());
        return existingUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
