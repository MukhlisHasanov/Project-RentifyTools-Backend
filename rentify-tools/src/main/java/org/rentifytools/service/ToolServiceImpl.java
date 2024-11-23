package org.rentifytools.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.entity.Tool;
import org.rentifytools.entity.User;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.ToolRepository;
import org.rentifytools.repository.UserRepository;
import org.rentifytools.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepository toolRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Override
    public List<ToolResponseDto> getAllTools() {
        return toolRepository.findAll().stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class)).toList();
    }

    public Tool findToolById(Long toolId) {
        String exceptionMessage = "Tool ID %d not found";
        return toolRepository.findById(toolId)
                .orElseThrow(() -> new NotFoundException(String.format(exceptionMessage, toolId)));
    }

    public User getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        String exceptionMessage = "User with ID %d not found";
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(exceptionMessage, userId)));
    }

    @Override
    public ToolResponseDto getToolById(Long toolId) {
        return mapper.map(findToolById(toolId), ToolResponseDto.class);
    }

    @Override
    public List<ToolResponseDto> getToolsByTitle(String toolName) {
        return toolRepository.findByTitle(toolName).stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class))
                .toList();
    }

    @Override
    public List<ToolResponseDto> getByTitleContaining(String toolName) {
        return toolRepository.findByTitleContaining(toolName).stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class))
                .toList();
    }

//   ===================================
//    @Override
//    public List<ToolResponseDto> getToolsByStatus(ToolsAvailabilityStatus status) {
//        if(status == null) {
//            return getAllTools();
//        } else {
//            List<Tool> toolByStatus = toolRepository.findByStatus(status);
//            return toolByStatus.stream()
//                    .map(tool -> mapper.map(tool, ToolResponseDto.class))
//                    .toList();
//        }
//    }
//   ===================================

    @Override
    public List<ToolResponseDto> getAllToolsByUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<Tool> toolsByUserId = toolRepository.findAllByUserId(userId);
        return toolsByUserId.stream()
                .map(tool -> mapper.map(tool, ToolResponseDto.class))
                .toList();
    }

    @Override
    @Transactional
    public ToolResponseDto addNewTool(ToolRequestDto dto) {
        Tool tool = mapper.map(dto, Tool.class);
        User user = getCurrentUser();
        tool.setUser(user);
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
        Tool tool = findToolById(toolId);
        tool.setStatus(status);
        tool = toolRepository.save(tool);
        return mapper.map(tool, ToolResponseDto.class);
    }

    @Override
    @Transactional
    public ToolResponseDto deleteTool(Long toolId) {
        Tool tool = findToolById(toolId);
        toolRepository.deleteById(toolId);
        return mapper.map(tool, ToolResponseDto.class);
    }
}