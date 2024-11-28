package org.rentifytools.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.rentifytools.dto.toolDto.ToolRequestDto;
import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.exception.NotFoundException;
import org.rentifytools.service.ToolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tool API", description = "Methods for working with tools")
@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    @Operation(summary = "Getting all tools from DB")
    @GetMapping
    public ResponseEntity<List<ToolResponseDto>> getAllTools() {
        List<ToolResponseDto> tools = toolService.getAllTools();
        if (tools.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ToolResponseDto>> getToolsByUserId() {
        List<ToolResponseDto> tools = toolService.getAllToolsByUser();
        if (tools.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }

    @Operation(summary = "Getting tool by id")
    @GetMapping("/{toolId}")
    public ToolResponseDto getToolById(@PathVariable(name = "toolId") Long toolId) {
        return toolService.getToolById(toolId);
    }

//    @Operation(summary = "Getting tools by name")
//    @GetMapping("/{toolName}")
//    public List<ToolResponseDto> getToolByTitle(@RequestParam(name = "toolName") String toolTitle) {
//        return toolService.getToolsByTitle(toolTitle);
//    }

    @Operation(summary = "Getting tools by name")
    @GetMapping("/search")
    public ResponseEntity<List<ToolResponseDto>> getTitleIfContains(@RequestParam(name = "name") String toolTitle) {
        List<ToolResponseDto> tools = toolService.getByTitleContaining(toolTitle);
        if (tools.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }


//    @Operation(summary = "Getting tools by status")
//    @GetMapping()
//    public List<ToolResponseDto> getToolsByStatus(@RequestParam(name = "status", required = false) ToolsAvailabilityStatus status) {
//        return toolService.getToolsByStatus(status);
//    }


    @Operation(summary = "Adding new tool to the list")
    @PostMapping
    public ResponseEntity<ToolResponseDto> addTool(@RequestBody ToolRequestDto dto) {
        return new ResponseEntity<>(toolService.addNewTool(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Editing tool information")
    @PutMapping("/{toolId}")
    public ResponseEntity<ToolResponseDto> editTool(@PathVariable(name = "toolId") Long toolId, @RequestBody ToolRequestDto dto) {
        return new ResponseEntity<>(toolService.updateTool(toolId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Changing the availability status of the tool")
    @PatchMapping("/{toolId}")
    public ToolResponseDto setToolStatus(@PathVariable(name = "toolId") Long toolId,
                                         @RequestParam(name = "status", required = true,  defaultValue = "AVAILABLE") ToolsAvailabilityStatus status) {
        return toolService.setToolStatus(toolId, status);
    }

    @Operation(summary = "Removing a tool from the list")
    @DeleteMapping("/{toolId}")
    public ToolResponseDto deleteTool(@PathVariable(name = "toolId") Long toolId) {
        return toolService.deleteTool(toolId);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> toolNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}