package org.rentifytools.service;

import lombok.RequiredArgsConstructor;
import org.rentifytools.entity.Tool;
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
        System.out.println(tool);
    }
}
