package com.javaclass.roundtable.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "class_table")
public class ClassTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "weekly")
    private String weekly;

    @Column(name = "date")
    private String date;

    @Column(name = "seq_no")
    private Integer seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    // Link to the user who is the lecturer/instructor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private SysUser instructor;

    // Legacy field for compatibility or simple display
    @Column(name = "speaker")
    private String speaker;

    @Min(value = 1, message = "Max participants must be at least 1")
    @Column(name = "max_participants")
    private Integer maxParticipants;
}
