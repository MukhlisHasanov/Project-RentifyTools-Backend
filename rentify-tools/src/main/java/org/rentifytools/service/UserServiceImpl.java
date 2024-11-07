package org.rentifytools.service;

import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        repository.findByEmail(dto.getEmail());
        return null;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return List.of();
    }

}
