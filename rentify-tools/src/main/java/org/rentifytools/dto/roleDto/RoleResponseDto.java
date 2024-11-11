package org.rentifytools.dto.roleDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "ResponseDTO class for User`s role")
public class RoleResponseDto {
    private Long id;
    @Schema(description = "Role`s status", example = "USER")
    private String title;
}
