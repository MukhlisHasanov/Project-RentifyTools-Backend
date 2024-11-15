package org.rentifytools.controller;

import lombok.RequiredArgsConstructor;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.service.ToolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    @GetMapping()
    public List<Tool> getAllTools() {
        return toolService.getAllTools();
    }

    @PostMapping
    public void addTool(@RequestBody Tool tool) {
        toolService.addTool(tool);
    }

    @DeleteMapping("{toolId}")
    public void deleteTool(@PathVariable("toolId") Long toolId) {
        toolService.deleteTool(toolId);
    }

    @PutMapping("{toolId}")
    public void updateTool(
            @PathVariable("toolId") Long toolId,
            @RequestParam(required = false) String toolName,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ToolsAvailabilityStatus status,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) Integer price) {
        toolService.updateTool(toolId, toolName, description, status, image, price);
    }

}
