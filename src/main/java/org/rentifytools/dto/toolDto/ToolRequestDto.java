package org.rentifytools.dto.toolDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "RequestDto class for Tool")
public class ToolRequestDto {

    @Size(min = 3, max = 50, message = "Advert title can be between 3 and 63 characters long")
    private String title;

    @Size(max = 1500, message = "Description may contain no more than 1500 characters.")
    private String description;

    private ToolsAvailabilityStatus status;

    private List<String> imageUrls;

    @DecimalMin(value = "0.0", message = "Price must be a positive number and can't be less than 0.")
    private Double price;

    private List<Long> categoryIds;
}