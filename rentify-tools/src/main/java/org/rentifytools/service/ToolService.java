package org.rentifytools.service;

import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;

import java.util.List;

public interface ToolService {
    List<ToolResponseDto> getAllTools();

    ToolResponseDto addNewTool(ToolRequestDto tool);

//    ToolResponseDto updateTool(Long toolId, Long ownerId,ToolRequestDto tool);
//
//    ToolResponseDto deleteTool(Long toolId);
}
