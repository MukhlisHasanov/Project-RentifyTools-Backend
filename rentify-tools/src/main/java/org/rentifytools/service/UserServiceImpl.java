package org.rentifytools.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.entity.Role;
import org.rentifytools.entity.User;
import org.rentifytools.exception.DuplicateEmailException;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        repository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new DuplicateEmailException(dto.getEmail());
                });

        User user = mapper.map(dto, User.class);

        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRoles(Set.of(roleService.getRole("USER")));

        User savedUser = repository.save(user);

        return mapper.map(savedUser, UserResponseDto.class);
    }


    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User foundUser = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));

        repository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new DuplicateEmailException(dto.getEmail());
                });

        mapper.map(dto, foundUser);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            foundUser.setPassword(encoder.encode(dto.getPassword()));
        }

        return mapper.map(repository.save(foundUser), UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return repository.findAll().stream()
                .map(user -> mapper.map(user, UserResponseDto.class))
                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return mapper.map(repository.findById(id), UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    @Transactional
    public UserResponseDto setRole(Long id, String title) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        Role roleAdmin = roleService.getRole(title);
        HashSet<Role> roles = new HashSet<>(entity.getRoles());
        roles.add(roleAdmin);
        entity.setRoles(roles);
        entity = repository.save(entity);
        return mapper.map(entity, UserResponseDto.class);
    }

    @Override
    @Transactional
    public UserResponseDto deleteUser(Long id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        repository.deleteById(id);
        return mapper.map(entity, UserResponseDto.class);
    }
}
