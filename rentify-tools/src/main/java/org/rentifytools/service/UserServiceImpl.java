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
import org.rentifytools.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper mapper;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        User user = repository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
//
//        return new CustomUserDetails(user);
//    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        repository.findByEmail(dto.getEmail())
                .ifPresent(user -> {throw new DuplicateEmailException(dto.getEmail());
                });
        HashSet<Role> roles = new HashSet<>();
        Role roleUser = roleService.getRole("USER");
        roles.add(roleUser);
        String encodedPass = encoder.encode(dto.getPassword());

        User user = User.builder()
                .firstName(dto.getFirstname())
                .lastName(dto.getLastname())
                .email(dto.getEmail())
                .password(encodedPass)
                .roles(roles)
                .phone(dto.getPhone())
                .build();
        User savedUser = repository.save(user);
        return mapper.map(savedUser, UserResponseDto.class);
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
