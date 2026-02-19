package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassTableRepository extends JpaRepository<ClassTable,Long> {
    List<ClassTable> findAllByOrderBySeqNoAsc();
    List<ClassTable> findByInstructor(SysUser instructor);
}
