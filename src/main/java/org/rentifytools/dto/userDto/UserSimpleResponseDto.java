package org.rentifytools.dto.userDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSimpleResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
