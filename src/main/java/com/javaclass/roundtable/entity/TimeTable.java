package com.javaclass.roundtable.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "time_table")
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "subject")
    private String subject;

    @Column(name = "week")
    private String week;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "order")
    private int order;


}
