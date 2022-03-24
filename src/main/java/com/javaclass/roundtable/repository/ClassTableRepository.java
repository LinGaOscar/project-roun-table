package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.ClassTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassTableRepository extends JpaRepository<ClassTable,Long> {
}
