package org.rentifytools.dto.toolDto;

import lombok.*;
import org.rentifytools.dto.urlImageDto.UrlImageDto;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ToolResponseDto {
    private Long id;
    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private List<UrlImageDto> images;
    private Double price;
}