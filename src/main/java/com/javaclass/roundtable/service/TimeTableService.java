package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.TimeTable;

import java.util.List;

public interface TimeTableService {

    TimeTable saveTable(TimeTable timeTable);

    TimeTable updateTable(TimeTable timeTable);

    List<TimeTable> findAll();

}
