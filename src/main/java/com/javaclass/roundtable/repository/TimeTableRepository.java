package com.javaclass.roundtable.repository;

import com.javaclass.roundtable.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable,Long> {
}
