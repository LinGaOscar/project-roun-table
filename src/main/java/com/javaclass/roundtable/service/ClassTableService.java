package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.ClassTable;

import java.util.List;

public interface ClassTableService {

    ClassTable saveTable(ClassTable classTable);

    ClassTable updateTable(ClassTable classTable);

    ClassTable findById(Long id);

    List<ClassTable> findAll();

}
