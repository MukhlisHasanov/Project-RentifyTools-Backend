package org.rentifytools.service;

import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;

import java.util.List;

public interface ToolService {
    List<ToolResponseDto> getAllTools();

    ToolResponseDto getToolById(Long toolId);
    Tool findToolById(Long toolId);

    ToolResponseDto addNewTool(ToolRequestDto tool);

    ToolResponseDto updateTool(Long toolId, ToolRequestDto dto);

    ToolResponseDto setToolStatus(Long toolId, ToolsAvailabilityStatus status);
//   ===================================
//    List<ToolResponseDto> getToolsByStatus(ToolsAvailabilityStatus status);
//   ===================================

    List<ToolResponseDto> getAllToolsByUser(Long userId);

    ToolResponseDto deleteTool(Long toolId);
}