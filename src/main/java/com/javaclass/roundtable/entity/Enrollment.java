package com.javaclass.roundtable.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SysUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassTable classTable;

    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;

    @Column(name = "status")
    private String status; // e.g., ENROLLED, CANCELLED, WAITING

    @PrePersist
    protected void onCreate() {
        enrollmentDate = LocalDateTime.now();
        if (status == null) {
            status = "ENROLLED";
        }
    }
}
