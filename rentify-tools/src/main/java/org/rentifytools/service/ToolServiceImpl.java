package org.rentifytools.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepository toolRepository;
    private final ModelMapper mapper;

    @Override
    public List<ToolResponseDto> getAllTools() {
        return toolRepository.findAll().stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class)).toList();
    }

//    @Override
//    public List<ToolResponseDto> getToolsByStatus(ToolsAvailabilityStatus status) {
//        if(status == null) {
//            return getAllTools();
//        } else {
//            List<Tool> toolBystatus = toolRepository.findByStatus(status);
//            return toolBystatus.stream()
//                    .map(tool -> mapper.map(tool, ToolResponseDto.class))
//                    .toList();
//        }
//    }

    @Override
    public ToolResponseDto addNewTool(ToolRequestDto dto) {
        Tool tool = mapper.map(dto, Tool.class);
        tool = toolRepository.save(tool);
        return mapper.map(tool, ToolResponseDto.class);
    }

    @Override
    @Transactional
    public ToolResponseDto updateTool(Long toolId, ToolRequestDto dto) {
        Tool tool = mapper.map(dto, Tool.class);
        tool.setId(toolId);
        tool = toolRepository.save(tool);
        return mapper.map(tool, ToolResponseDto.class);
    }

    @Override
    @Transactional
    public ToolResponseDto setToolStatus(Long toolId, ToolsAvailabilityStatus status) {
        String exceptionMessage = "Failed to change availability status of the tool. Product ID %d not found";
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new NotFoundException(String.format(exceptionMessage, toolId)));
        tool.setStatus(status);
        tool = toolRepository.save(tool);
        return mapper.map(tool, ToolResponseDto.class);
    }


//    @Override
//    public ToolResponseDto deleteTool(Long toolId) {
//        return null;
//    }


    @Override
    public List<ToolResponseDto> getAllToolsByUser(Long userId) {
        return toolRepository.findByUserId(userId);
    }
}