package org.rentifytools.service;

import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    UserResponseDto setRole(Long id, String title);
    UserResponseDto deleteUser(Long id);
}
