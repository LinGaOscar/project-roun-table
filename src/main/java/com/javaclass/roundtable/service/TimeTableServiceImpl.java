package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.TimeTable;
import com.javaclass.roundtable.repository.TimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TimeTableServiceImpl implements TimeTableService {
    private TimeTableRepository timeTableRepository;

    @Autowired
    public void autoWired(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }


    @Override
    public TimeTable saveTable(TimeTable timeTable) {
        return timeTableRepository.save(timeTable);
    }

    @Override
    public TimeTable updateTable(TimeTable timeTable) {
        return timeTableRepository.save(timeTable);
    }

    @Override
    public List<TimeTable> findAll() {
        return timeTableRepository.findAll();
    }
}
