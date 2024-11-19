package org.rentifytools.controller;

import org.rentifytools.dto.rentToolsDto.RentRequestDto;
import org.rentifytools.entity.Tool;
import org.rentifytools.repository.ToolRepository;
import org.rentifytools.service.RentService;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    private final ToolRepository toolRepository;
    private final RentService rentService;

    public RentController(ToolRepository toolRepository, RentService rentService) {
        this.toolRepository = toolRepository;
        this.rentService = rentService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> rentTool(@PathVariable Long id, @RequestBody RentRequestDto rentRequest) {

        Tool tool = toolRepository.findById(id).orElse(null);
        if (tool == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tool nicht gefunden");
        }


        if (tool.getStatus() != ToolsAvailabilityStatus.AVAILABLE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tool ist bereits vermietet");
        }


        rentService.rentTool(tool, rentRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Tool erfolgreich gemietet");
    }
}


