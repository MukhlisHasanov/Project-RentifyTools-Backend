package org.rentifytools.dto.toolDto;

import lombok.*;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.repository.ToolRepository;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ToolRequestDto {
    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private List<String> imageUrls;
    private Double price;
}