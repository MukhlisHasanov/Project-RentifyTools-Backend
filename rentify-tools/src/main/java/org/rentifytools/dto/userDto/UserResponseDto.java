package org.rentifytools.dto.userDto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "ResponseDTO class for User")
public class UserResponseDto {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User firstname", example = "John")
    private String firstname;

    @Schema(description = "User lastname", example = "Johnson")
    private String lastname;

    @Schema(description = "User email", example = "john@example.com")
    private String email;

    @Schema(description = "User phone number", example = "123-456-789")
    private String phone;

    @Schema(description = "User status", example = "USER")
    private Set<RoleResponseDto> roles;
}
