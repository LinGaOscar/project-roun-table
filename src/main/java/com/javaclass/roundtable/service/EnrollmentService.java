package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.Enrollment;

public interface EnrollmentService {
    Enrollment enroll(Long userId, Long classId);
    boolean isEnrolled(Long userId, Long classId);
}
