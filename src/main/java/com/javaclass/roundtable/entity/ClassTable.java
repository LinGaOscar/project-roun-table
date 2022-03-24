package com.javaclass.roundtable.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "class_table")
public class ClassTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subject")
    private String subject;

    @Column(name = "week")
    private String week;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "order")
    private Integer order;


}
