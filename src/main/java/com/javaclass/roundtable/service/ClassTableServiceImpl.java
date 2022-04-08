package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.repository.ClassTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassTableServiceImpl implements ClassTableService {
    private ClassTableRepository classTableRepository;

    @Autowired
    public void autoWired(ClassTableRepository classTableRepository) {
        this.classTableRepository = classTableRepository;
    }

    @Override
    public ClassTable saveTable(ClassTable classTable) {
        return classTableRepository.save(classTable);
    }

    @Override
    public ClassTable updateTable(ClassTable classTable) {
        return classTableRepository.save(classTable);
    }

    @Override
    public ClassTable findById(Long id) {
        return classTableRepository.findById(id).orElse(null);
    }

    @Override
    public List<ClassTable> findAll() {
        return classTableRepository.findAll();
    }
}
