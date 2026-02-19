package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.ClassTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassTableRepository extends JpaRepository<ClassTable,Long> {
    List<ClassTable> findAllByOrderBySeqNoAsc();
}
