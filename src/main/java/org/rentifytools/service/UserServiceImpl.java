package org.rentifytools.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.rentifytools.dto.userDto.UserLoginDto;
import org.rentifytools.dto.userDto.UserRequestDto;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.entity.Address;
import org.rentifytools.entity.Role;
import org.rentifytools.entity.User;
import org.rentifytools.exception.DuplicateEmailException;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.AddressRepository;
import org.rentifytools.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AddressRepository addressRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper mapper;

    @Override
    public void checkEmail(UserLoginDto dto) {
        repository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new DuplicateEmailException(dto.getEmail());
                });
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        repository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new DuplicateEmailException(dto.getEmail());
                });

        repository.findByPhone(dto.getPhone())
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
                });

        User user = mapper.map(dto, User.class);

        if (dto.getAddress() != null) {
            Address address = mapper.map(dto.getAddress(), Address.class);
            Optional<Address> existingAddress = addressRepository.findAll().stream()
                    .filter(address::equals)
                    .findFirst();
            Address savedAddress = existingAddress.orElseGet(() -> addressRepository.save(address));
            user.setAddress(savedAddress);
        }

        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRoles(Set.of(roleService.getRole("USER")));

        User savedUser = repository.save(user);

        return mapper.map(savedUser, UserResponseDto.class);
    }


    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        // Ищем пользователя
        User foundUser = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));

        // Обновляем поля пользователя
        mapper.map(dto, foundUser);

        // Обновляем пароль, если он указан
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            foundUser.setPassword(encoder.encode(dto.getPassword()));
        }

        // Работа с адресом
        if (dto.getAddress() != null) {
            Address currentAddress = foundUser.getAddress(); // Текущий адрес пользователя
            Address newAddress = mapper.map(dto.getAddress(), Address.class);

            if (currentAddress != null) {
                // Проверяем, изменились ли поля адреса
                if (!currentAddress.equals(newAddress)) {
                    // Проверяем, используется ли адрес другими пользователями
                    long usersWithAddressCount = repository.countByAddressId(currentAddress.getId());

                    if (usersWithAddressCount > 1) {
                        // Адрес используется другими пользователями, создаём новый адрес
                        Address savedAddress = addressRepository.save(newAddress);
                        foundUser.setAddress(savedAddress);
                    } else {
                        // Адрес больше никому не принадлежит, обновляем его
                        mapper.map(dto.getAddress(), currentAddress);
                        addressRepository.save(currentAddress);
                    }
                }
            } else {
                // Если адреса не было, создаём новый
                Address savedAddress = addressRepository.save(newAddress);
                foundUser.setAddress(savedAddress);
            }
        }

        // Сохраняем изменения пользователя
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
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getUsersByLastname(String lastname) {
        return repository.findAllByLastName(lastname).stream()
                .map(user -> mapper.map(user, UserResponseDto.class))
                .toList();
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByPhone(String phone) {
        User user = repository.findByPhone(phone)
                .orElseThrow(() -> new NotFoundException("User not found with phone: " + phone));
        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    @Transactional
    public UserResponseDto setRole(Long id, String title) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        Role roleAdmin = roleService.getRole(title);
        HashSet<Role> roles = new HashSet<>(entity.getRoles());
        if (roles.contains(roleAdmin)) {
            roles.remove(roleAdmin);
        } else {
            roles.add(roleAdmin);
        }
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
