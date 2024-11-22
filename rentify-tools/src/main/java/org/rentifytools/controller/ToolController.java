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

    @Operation(summary = "Getting all tools from DB")
    @GetMapping
    public List<ToolResponseDto> getAllTools() {
        return toolService.getAllTools();
    }

    @Operation(summary = "Getting tool by id")
    @GetMapping("/findById/{toolId}")
    public ToolResponseDto getToolById(@PathVariable(name = "toolId") Long toolId) {
        return toolService.getToolById(toolId);
    }

    @Operation(summary = "Getting tools by name")
    @GetMapping("/findByName/{toolName}")
    public List<ToolResponseDto> getToolByTitle(@PathVariable(name = "toolName") String toolTitle) {
        return toolService.getToolsByTitle(toolTitle);
    }

    @Operation(summary = "Getting tools by name")
    @GetMapping("/findAllByName/{toolName}")
    public List<ToolResponseDto> getTitleIfContains(@PathVariable(name = "toolName") String toolTitle) {
        return toolService.getByTitleContaining(toolTitle);
    }


//    @Operation(summary = "Getting tools by status")
//    @GetMapping()
//    public List<ToolResponseDto> getToolsByStatus(@RequestParam(name = "status", required = false) ToolsAvailabilityStatus status) {
//        return toolService.getToolsByStatus(status);
//    }


    @PostMapping
    public ToolResponseDto addTool(@RequestBody ToolRequestDto dto) {
        return toolService.addNewTool(dto);
    }

    @Operation(summary = "Editing tool information")
    @PutMapping("/edit/{toolId}")
    public ToolResponseDto editTool(@PathVariable(name = "toolId") Long toolId, @RequestBody ToolRequestDto dto) {
        return toolService.updateTool(toolId, dto);
    }

    @Operation(summary = "Changing the availability status of the tool")
    @PatchMapping("/status/{toolId}")
    public ToolResponseDto setToolStatus(@PathVariable(name = "toolId") Long toolId,
                                         @RequestParam(name = "status", required = true,  defaultValue = "AVAILABLE") ToolsAvailabilityStatus status) {
        return toolService.setToolStatus(toolId, status);
    }

    @Operation(summary = "Removing a tool from the list")
    @DeleteMapping("{toolId}")
    public ToolResponseDto deleteTool(@PathVariable(name = "toolId") Long toolId) {
        return toolService.deleteTool(toolId);
    }

}