package org.rentifytools.dto.toolDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rentifytools.dto.userDto.UserResponseDto;
import org.rentifytools.entity.User;
import org.rentifytools.enums.ToolsAvailabilityStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ToolResponseDto {
    private Long id;
//    private UserResponseDto user;
    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private String image;
    private Double price;
}