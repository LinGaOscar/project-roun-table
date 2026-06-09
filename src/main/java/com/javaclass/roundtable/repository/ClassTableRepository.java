package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ClassTableRepository extends JpaRepository<ClassTable,Long> {
    List<ClassTable> findAllByOrderBySeqNoAsc();
    Page<ClassTable> findAllByOrderBySeqNoAsc(Pageable pageable);
    List<ClassTable> findByInstructor(SysUser instructor);
    List<ClassTable> findByInstructorId(Long instructorId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM ClassTable c WHERE c.id = :id")
    Optional<ClassTable> findByIdWithLock(@Param("id") Long id);
}
