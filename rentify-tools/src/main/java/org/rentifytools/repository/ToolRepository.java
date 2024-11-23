package org.rentifytools.repository;

import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long>{

    List<Tool> findAllByUserId(Long userId);
//    List<Tool> findByStatus(ToolsAvailabilityStatus status);

//   ===================================

    List<Tool> findByTitle(String title);
    List<Tool> findByTitleContaining(String title);
    List<Tool> findByTitleAndStatus(String title, ToolsAvailabilityStatus status);

    List<ToolResponseDto> findByUserId(Long userId);

    void deleteById(Long toolId);

}

