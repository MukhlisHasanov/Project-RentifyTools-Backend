package org.rentifytools.service;


import org.rentifytools.dto.rentToolsDto.RentRequestDto;
import org.rentifytools.entity.Tool;
import org.rentifytools.enums.ToolsAvailabilityStatus;
import org.rentifytools.repository.ToolRepository;
import org.springframework.stereotype.Service;

@Service
public class RentService {



        private final ToolRepository toolRepository;

        public RentService(ToolRepository toolRepository) {
            this.toolRepository = toolRepository;
        }

        public void rentTool(Tool tool, RentRequestDto rentRequest) {

            tool.setStatus(ToolsAvailabilityStatus.RENTED);
            /* v181124 Hier können weitere Geschäftslogiken wie das Speichern des Mietvorgangs in einer Datenbank eingefügt werden.*/
            toolRepository.save(tool);
        }
    }


