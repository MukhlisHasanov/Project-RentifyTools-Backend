package org.rentifytools.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
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
    public List<ToolResponseDto> getAllTools() {
        return toolService.getAllTools();
    }

//    @GetMapping()
//    public List<ToolResponseDto> getToolsByStatus(@RequestParam(name = "status", required = false) ToolsAvailabilityStatus status) {
//        return toolService.getToolsByStatus(status);
//    }

    @PostMapping
    public ToolResponseDto addTool(@RequestBody ToolRequestDto dto) {
        return toolService.addNewTool(dto);
    }

    @Operation(summary = "Editing tool information")
    @PutMapping("{toolId}")
    public ToolResponseDto editTool(@PathVariable(name = "toolId") Long toolId, @RequestBody ToolRequestDto dto) {
        return toolService.updateTool(toolId, dto);
    }

    @Operation(summary = "Changing the availability status of the tool")
    @PatchMapping("{toolId}")
    public ToolResponseDto setToolStatus(@PathVariable(name = "toolId") Long toolId,
                                         @RequestParam(name = "status", required = true,  defaultValue = "AVAILABLE") ToolsAvailabilityStatus status) {
        return toolService.setToolStatus(toolId, status);
    }

}