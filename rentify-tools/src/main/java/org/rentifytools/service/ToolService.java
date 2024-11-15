package org.rentifytools.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.repository.ToolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public void addTool(Tool tool) {
        toolRepository.save(tool);
    }

    public void deleteTool(Long toolId) {
        toolRepository.findById(toolId).
                orElseThrow(() -> new NotFoundException("Tool with id " + toolId + " not found"));
        toolRepository.deleteById(toolId);
    }

    @Transactional
    public void updateTool(Long toolId,
                           String toolName,
                           String description,
                           ToolsAvailabilityStatus status,
                           String image,
                           Integer price) {
        Tool tool = toolRepository.findById(toolId).
                orElseThrow(() -> new NotFoundException("Tool with id " + toolId + " not found"));

        if (toolName != null && toolName.length() > 2) {
            tool.setTitle(toolName);
        }

        if (description != null && description.length() > 2) {
            tool.setDescription(description);
        }

        if (status != null) {
            tool.setStatus(status);
        }

        if (image != null && image.length() > 4) {
            tool.setImage(image);
        }

        if (price != null && price > 0) {
            tool.setPrice(price);
        }
    }
}
