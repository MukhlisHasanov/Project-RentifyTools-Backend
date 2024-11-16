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

    @Override
    public ToolResponseDto addNewTool(ToolRequestDto dto) {
        Tool tool = mapper.map(dto, Tool.class);
        tool = toolRepository.save(tool);
        return mapper.map(tool, ToolResponseDto.class);
    }

//    @Override
//    public ToolResponseDto updateTool(Long toolId, Long ownerId, ToolRequestDto tool) {
//        return null;
//    }
//
//    @Override
//    public ToolResponseDto deleteTool(Long toolId) {
//        return null;
//    }

// ===================================================

//    public void deleteTool(Long toolId) {
//        toolRepository.findById(toolId).
//                orElseThrow(() -> new NotFoundException("Tool with id " + toolId + " not found"));
//        toolRepository.deleteById(toolId);
//    }
//
//    @Transactional
//    public void updateTool(Long toolId,
//                           String toolName,
//                           String description,
//                           ToolsAvailabilityStatus status,
//                           String image,
//                           Integer price) {
//        Tool tool = toolRepository.findById(toolId).
//                orElseThrow(() -> new NotFoundException("Tool with id " + toolId + " not found"));
//
//        if (toolName != null && toolName.length() > 2) {
//            tool.setTitle(toolName);
//        }
//
//        if (description != null && description.length() > 2) {
//            tool.setDescription(description);
//        }
//
//        if (status != null) {
//            tool.setStatus(status);
//        }
//
//        if (image != null && image.length() > 4) {
//            tool.setImage(image);
//        }
//
//        if (price != null && price > 0) {
//            tool.setPrice(price);
//        }
//    }
}