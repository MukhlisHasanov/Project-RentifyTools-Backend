package org.rentifytools.repository;

import org.rentifytools.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long>{

    @Query("SELECT tool FROM Tool tool WHERE tool.title = ?1")
    Optional<Tool> findByName(String toolName);

}


