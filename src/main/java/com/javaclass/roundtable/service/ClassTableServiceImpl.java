package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.repository.ClassTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class ClassTableServiceImpl implements ClassTableService {
    private final ClassTableRepository classTableRepository;

    public ClassTableServiceImpl(ClassTableRepository classTableRepository) {
        this.classTableRepository = classTableRepository;
    }

    @Override
    public ClassTable saveTable(ClassTable classTable) {
        log.info("Saving new class table entry: {}", classTable.getTitle());
        return classTableRepository.save(classTable);
    }

    @Override
    public ClassTable updateTable(ClassTable classTable) {
        log.info("Updating class table entry ID: {}", classTable.getId());
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

    @Override
    public List<ClassTable> findAllOrderBySeqNo() {
        log.debug("Fetching all class tables ordered by SeqNo");
        return classTableRepository.findAllByOrderBySeqNoAsc();
    }

    @Override
    public List<ClassTable> findByInstructorId(Long instructorId) {
        log.debug("Fetching classes for instructor ID: {}", instructorId);
        return classTableRepository.findByInstructorId(instructorId);
    }
}
