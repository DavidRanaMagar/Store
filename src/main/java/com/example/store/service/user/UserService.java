package com.example.store.service.user;

import com.example.store.dto.UserDto;
import com.example.store.exceptions.ResourceAlreadyExistsException;
import com.example.store.exceptions.ResourceNotFoundException;
import com.example.store.model.Role;
import com.example.store.model.User;
import com.example.store.repository.RoleRepository;
import com.example.store.repository.UserRepository;
import com.example.store.request.user.AddUserRequest;
import com.example.store.request.user.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username Not Found"));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete,
                        () -> {throw new ResourceNotFoundException("User Not Found");});
    }

    @Override
    public User addUser(AddUserRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .<User>map(existingUser -> {
                    throw new ResourceAlreadyExistsException("Username Already Exists");
                })
                .orElseGet(() -> userRepository.save(createUser(request)));
    }

    private User createUser(AddUserRequest request) {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        """
                        Role ID {request.getRoleId()} Not Found
                        """
                ));

        return new User(
                request.getUsername(),
                request.getPassword(),
                request.getCreatedBy(),
                role
        );
    }

    @Override
    public User updateUser(UserUpdateRequest request, int userId) {
        return userRepository.findById(userId)
                .map(existingUser -> updateExistingUser(existingUser, request))
                .map(userRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    private User updateExistingUser(User existingUser, UserUpdateRequest request) {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role ID {request.getRoleId()} Not Found"));

        existingUser.setPassword(request.getPassword());
        existingUser.setUpdatedBy(request.getUpdatedBy());
        existingUser.setRole(role);
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
