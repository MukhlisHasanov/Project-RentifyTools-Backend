package org.rentifytools.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rentifytools.dto.roleDto.RoleResponseDto;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private Set<RoleResponseDto> roles;
}
