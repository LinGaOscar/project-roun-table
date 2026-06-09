package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.ClassTable;
import com.javaclass.roundtable.entity.Enrollment;
import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.exception.BusinessException;
import com.javaclass.roundtable.repository.ClassTableRepository;
import com.javaclass.roundtable.repository.EnrollmentRepository;
import com.javaclass.roundtable.repository.SysUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private SysUserRepository sysUserRepository;

    @Mock
    private ClassTableRepository classTableRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    // --- enroll() tests ---

    @Test
    void enroll_throwsBusinessException_whenUserNotFound() {
        when(sysUserRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollmentService.enroll(99L, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User not found.");

        verifyNoInteractions(classTableRepository, enrollmentRepository);
    }

    @Test
    void enroll_throwsBusinessException_whenClassNotFound() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setAccount("user01");

        when(sysUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(classTableRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollmentService.enroll(1L, 99L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Class not found.");

        verifyNoInteractions(enrollmentRepository);
    }

    @Test
    void enroll_throwsBusinessException_whenAlreadyEnrolled() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setAccount("user01");

        ClassTable classTable = new ClassTable();
        classTable.setId(1L);
        classTable.setTitle("Test Class");

        Enrollment existing = new Enrollment();

        when(sysUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(classTableRepository.findById(1L)).thenReturn(Optional.of(classTable));
        when(enrollmentRepository.findByUserAndClassTable(user, classTable))
                .thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> enrollmentService.enroll(1L, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("You have already enrolled in this class.");
    }

    @Test
    void enroll_throwsBusinessException_whenClassIsFull() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setAccount("user01");

        ClassTable classTable = new ClassTable();
        classTable.setId(1L);
        classTable.setTitle("Full Class");
        classTable.setMaxParticipants(10);

        when(sysUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(classTableRepository.findById(1L)).thenReturn(Optional.of(classTable));
        when(enrollmentRepository.findByUserAndClassTable(user, classTable)).thenReturn(Optional.empty());
        when(enrollmentRepository.countByClassTableAndStatus(classTable, "ENROLLED")).thenReturn(10L);

        assertThatThrownBy(() -> enrollmentService.enroll(1L, 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("This class is already full.");

        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void enroll_returnsEnrollment_whenSuccessful_noCapLimit() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setAccount("user01");

        ClassTable classTable = new ClassTable();
        classTable.setId(1L);
        classTable.setTitle("Open Class");
        // maxParticipants is null — no cap check

        Enrollment saved = new Enrollment();
        saved.setId(42L);
        saved.setUser(user);
        saved.setClassTable(classTable);

        when(sysUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(classTableRepository.findById(1L)).thenReturn(Optional.of(classTable));
        when(enrollmentRepository.findByUserAndClassTable(user, classTable)).thenReturn(Optional.empty());
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(saved);

        Enrollment result = enrollmentService.enroll(1L, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(42L);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getClassTable()).isEqualTo(classTable);

        // countByClassTableAndStatus must NOT be called when maxParticipants is null
        verify(enrollmentRepository, never()).countByClassTableAndStatus(any(), any());
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void enroll_returnsEnrollment_whenSuccessful_withCapNotYetFull() {
        SysUser user = new SysUser();
        user.setId(2L);
        user.setAccount("user02");

        ClassTable classTable = new ClassTable();
        classTable.setId(2L);
        classTable.setTitle("Capped Class");
        classTable.setMaxParticipants(5);

        Enrollment saved = new Enrollment();
        saved.setId(7L);

        when(sysUserRepository.findById(2L)).thenReturn(Optional.of(user));
        when(classTableRepository.findById(2L)).thenReturn(Optional.of(classTable));
        when(enrollmentRepository.findByUserAndClassTable(user, classTable)).thenReturn(Optional.empty());
        when(enrollmentRepository.countByClassTableAndStatus(classTable, "ENROLLED")).thenReturn(3L);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(saved);

        Enrollment result = enrollmentService.enroll(2L, 2L);

        assertThat(result.getId()).isEqualTo(7L);
        verify(enrollmentRepository).save(any(Enrollment.class));
    }
}
