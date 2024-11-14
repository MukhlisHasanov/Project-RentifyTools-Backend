package org.rentifytools.entity;

import jakarta.persistence.*;
import lombok.*;
import org.rentifytools.enums.ToolsAvailabilityStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ownerId")
    private Long ownerId;


    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private String image;
    private Integer price;


}
