package org.rentifytools.dto.userDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "RequestDTO class for User")
public class UserRequestDto {
    @NotBlank
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^\\p{Lu}\\p{L}*", message = "Invalid name format")
    @Schema(description = "User firstname", example = "John")
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^\\p{Lu}\\p{L}*", message = "Invalid name format")
    @Schema(description = "User lastname", example = "Johnson")
    private String lastname;

    @Size(min = 5, max = 35)
    @Email(message = "Invalid email format")
    @Schema(description = "User email", example = "john@example.com")
    private String email;

    @Size(min = 3, max = 15)
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$", message = "Should be strong")
    @Schema(description = "User password", example = "john1234")
    private String password;

    @Size(min = 6, max = 14)
    @Pattern(regexp = "^\\+?\\d{1,3}?[- .]?\\(?\\d{1,4}?\\)?[- .]?\\d{1,4}[- .]?\\d{1,9}$", message = "Invalid phone number")
    @Schema(description = "User phone number", example = "123-456-789")
    private String phone;
}
