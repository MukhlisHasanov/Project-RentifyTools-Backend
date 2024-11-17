package org.rentifytools.dto.toolDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.repository.ToolRepository;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ToolRequestDto {
    private String title;
    private String description;
    private ToolsAvailabilityStatus status = ToolsAvailabilityStatus.AVAILABLE;
    private String image;
    private Double price;
}