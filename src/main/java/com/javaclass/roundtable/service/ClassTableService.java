package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.ClassTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ClassTableService {
    ClassTable saveTable(ClassTable classTable);
    ClassTable updateTable(ClassTable classTable);
    ClassTable findById(Long id);
    List<ClassTable> findAll();
    List<ClassTable> findAllOrderBySeqNo();
    Page<ClassTable> findAllOrderBySeqNo(Pageable pageable);
    List<ClassTable> findByInstructorId(Long instructorId);
    void deleteTable(Long id);
}
