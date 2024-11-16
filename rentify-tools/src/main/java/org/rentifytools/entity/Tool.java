package org.rentifytools.entity;

import jakarta.persistence.*;
import lombok.*;
import org.rentifytools.enums.ToolsAvailabilityStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "ownerId", nullable = false, updatable = false)
    private Long ownerId;

//    @OneToOne (mappedBy = "tools", cascade = CascadeType.ALL)
//    private Long ownerId;

    private String title;
    private String description;
    private ToolsAvailabilityStatus status;
    private String image;
    private Integer price;
}
