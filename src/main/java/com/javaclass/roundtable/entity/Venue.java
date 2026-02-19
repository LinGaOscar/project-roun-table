package com.javaclass.roundtable.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;
}
