package org.rentifytools.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.N;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "tools")
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @ManyToMany(mappedBy = "categories")
    private Set<Tool> tools = new HashSet<Tool>();
}
