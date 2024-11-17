package org.rentifytools.dto.toolDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ToolRequestDto {
    private String title;
    private String description;
    private String image;
    private Double price;
}
