package org.rentifytools.repository;

import org.rentifytools.dto.toolDto.ToolResponseDto;
import org.rentifytools.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long>{

//    List<Tool> findByStatus(ToolsAvailabilityStatus status);

    List<ToolResponseDto> findByUserId(Long userId);

    @Query("SELECT tool FROM Tool tool WHERE tool.title = ?1")
    Optional<Tool> findByName(String toolName);

}

