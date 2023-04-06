package org.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "astronauts")
@NoArgsConstructor
@Getter
@Setter
public class Astronauts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Astronauts(String name) {
        this.name = name;
    }
}
