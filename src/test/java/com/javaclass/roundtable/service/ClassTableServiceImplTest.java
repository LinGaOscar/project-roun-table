package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.repository.ClassTableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassTableServiceImplTest {

    @Mock
    private ClassTableRepository classTableRepository;

    @InjectMocks
    private ClassTableServiceImpl classTableService;

    @Test
    void saveTable_callsRepositorySaveAndReturnsResult() {
        ClassTable classTable = new ClassTable();
        classTable.setTitle("Spring Boot Fundamentals");

        ClassTable saved = new ClassTable();
        saved.setId(1L);
        saved.setTitle("Spring Boot Fundamentals");

        when(classTableRepository.save(classTable)).thenReturn(saved);

        ClassTable result = classTableService.saveTable(classTable);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Spring Boot Fundamentals");
        verify(classTableRepository).save(classTable);
    }

    @Test
    void deleteTable_callsRepositoryDeleteById() {
        Long id = 5L;

        classTableService.deleteTable(id);

        verify(classTableRepository).deleteById(id);
    }

    @Test
    void findById_returnsNull_whenNotFound() {
        when(classTableRepository.findById(999L)).thenReturn(Optional.empty());

        ClassTable result = classTableService.findById(999L);

        assertThat(result).isNull();
        verify(classTableRepository).findById(999L);
    }

    @Test
    void findById_returnsClassTable_whenFound() {
        ClassTable classTable = new ClassTable();
        classTable.setId(3L);
        classTable.setTitle("Advanced JPA");

        when(classTableRepository.findById(3L)).thenReturn(Optional.of(classTable));

        ClassTable result = classTableService.findById(3L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getTitle()).isEqualTo("Advanced JPA");
    }
}
