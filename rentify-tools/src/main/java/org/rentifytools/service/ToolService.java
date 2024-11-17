package org.rentifytools.service;

import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

public interface ToolService {
    List<ToolResponseDto> getAllTools();

    ToolResponseDto addNewTool(ToolRequestDto tool);

    ToolResponseDto updateTool(Long toolId, ToolRequestDto dto);

    ToolResponseDto setToolStatus(Long toolId, ToolsAvailabilityStatus status);

//    List<ToolResponseDto> getToolsByStatus(ToolsAvailabilityStatus status);

//    ToolResponseDto deleteTool(Long toolId);

    List<ToolResponseDto> getAllToolsByUser(Long userId);
}