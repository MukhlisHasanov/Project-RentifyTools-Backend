package org.rentifytools.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rentifytools.dto.roleDto.RoleResponseDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Set<RoleResponseDto> roles;
    private Set<ToolResponseDto> tools;
}
