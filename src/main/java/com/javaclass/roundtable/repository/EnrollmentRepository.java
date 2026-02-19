package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.Enrollment;
import com.javaclass.roundtable.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserAndClassTable(SysUser user, ClassTable classTable);
    long countByClassTableAndStatus(ClassTable classTable, String status);
    List<Enrollment> findByUserOrderByEnrollmentDateDesc(SysUser user);
}
