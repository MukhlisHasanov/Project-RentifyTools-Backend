package org.rentifytools.service;

import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto setRole(Long id, String title);
    UserResponseDto deleteUser(Long id);
}
