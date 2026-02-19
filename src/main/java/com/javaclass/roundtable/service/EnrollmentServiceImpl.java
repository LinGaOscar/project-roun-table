package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.Enrollment;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.repository.ClassTableRepository;
import com.javaclass.roundtable.repository.EnrollmentRepository;
import com.javaclass.roundtable.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final SysUserRepository sysUserRepository;
    private final ClassTableRepository classTableRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, 
                                 SysUserRepository sysUserRepository, 
                                 ClassTableRepository classTableRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.sysUserRepository = sysUserRepository;
        this.classTableRepository = classTableRepository;
    }

    @Override
    @Transactional
    public Enrollment enroll(Long userId, Long classId) {
        SysUser user = sysUserRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found."));
        ClassTable classTable = classTableRepository.findById(classId)
                .orElseThrow(() -> new BusinessException("Class not found."));

        // 1. Check if already enrolled
        if (enrollmentRepository.findByUserAndClassTable(user, classTable).isPresent()) {
            throw new BusinessException("You have already enrolled in this class.");
        }

        // 2. Check capacity
        if (classTable.getMaxParticipants() != null) {
            long currentEnrollments = enrollmentRepository.countByClassTableAndStatus(classTable, "ENROLLED");
            if (currentEnrollments >= classTable.getMaxParticipants()) {
                throw new BusinessException("This class is already full.");
            }
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setClassTable(classTable);
        
        log.info("User {} enrolled in class {}", user.getAccount(), classTable.getTitle());
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public boolean isEnrolled(Long userId, Long classId) {
        SysUser user = sysUserRepository.findById(userId).orElse(null);
        ClassTable classTable = classTableRepository.findById(classId).orElse(null);
        if (user == null || classTable == null) return false;
        return enrollmentRepository.findByUserAndClassTable(user, classTable).isPresent();
    }
}
