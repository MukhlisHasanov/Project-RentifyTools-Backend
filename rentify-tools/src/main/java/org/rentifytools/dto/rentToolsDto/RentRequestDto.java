package org.rentifytools.dto.rentToolsDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentRequestDto {
    private Long toolId;
    private String userName;
    private String userEmail;
    private String userPhone;
}
