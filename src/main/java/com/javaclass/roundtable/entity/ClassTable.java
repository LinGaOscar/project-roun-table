package com.javaclass.roundtable.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "class_table")
public class ClassTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "weekly")
    private String weekly;

    @Column(name = "speaker")
    private String speaker;

    @Column(name = "date")
    private String date;

    @Column(name = "seq_no")
    private Integer seqNo;
    
}
