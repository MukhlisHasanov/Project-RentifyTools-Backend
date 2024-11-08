package org.rentifytools.dto.userDto;

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
public class UserRequestDto {
    @NotBlank
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^\\p{Lu}\\p{L}*", message = "Invalid name format")
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 15)
    @Pattern(regexp = "^\\p{Lu}\\p{L}*", message = "Invalid name format")
    private String lastname;

    @Size(min = 5, max = 35)
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 3, max = 15)
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$", message = "Should be strong")
    private String password;

    @Size(min = 6, max = 14)
    @Pattern(regexp = "^\\+?\\d{1,3}?[- .]?\\(?\\d{1,4}?\\)?[- .]?\\d{1,4}[- .]?\\d{1,9}$", message = "Invalid phone number")
    private String phone;
}
