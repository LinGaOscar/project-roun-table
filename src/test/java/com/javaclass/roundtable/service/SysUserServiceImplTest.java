package com.javaclass.roundtable.service;

import com.javaclass.roundtable.entity.SysUser;
import com.javaclass.roundtable.repository.SysUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SysUserServiceImplTest {

    @Mock
    private SysUserRepository sysUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SysUserServiceImpl sysUserService;

    @Test
    void saveUser_encodesPasswordBeforeSaving() {
        SysUser user = new SysUser();
        user.setAccount("newuser");
        user.setPassword("plaintext");

        SysUser saved = new SysUser();
        saved.setId(1L);
        saved.setAccount("newuser");
        saved.setPassword("$2a$encoded");

        when(passwordEncoder.encode("plaintext")).thenReturn("$2a$encoded");
        when(sysUserRepository.save(user)).thenReturn(saved);

        SysUser result = sysUserService.saveUser(user);

        // Verify the password was encoded before save
        verify(passwordEncoder).encode("plaintext");
        verify(sysUserRepository).save(user);
        // The user object should have its password set to the encoded value
        assertThat(user.getPassword()).isEqualTo("$2a$encoded");
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void updateUser_preservesExistingPasswordHash_whenNewPasswordIsBlank() {
        SysUser incoming = new SysUser();
        incoming.setId(10L);
        incoming.setAccount("user10");
        incoming.setPassword(""); // blank — should preserve existing

        SysUser existing = new SysUser();
        existing.setId(10L);
        existing.setPassword("$2a$existinghash");

        SysUser saved = new SysUser();
        saved.setId(10L);
        saved.setPassword("$2a$existinghash");

        when(sysUserRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(sysUserRepository.save(incoming)).thenReturn(saved);

        SysUser result = sysUserService.updateUser(incoming);

        // passwordEncoder.encode must NOT be called
        verify(passwordEncoder, never()).encode(anyString());
        // The incoming user's password should be replaced with the existing hash
        assertThat(incoming.getPassword()).isEqualTo("$2a$existinghash");
        assertThat(result.getPassword()).isEqualTo("$2a$existinghash");
    }

    @Test
    void updateUser_preservesExistingPasswordHash_whenNewPasswordIsNull() {
        SysUser incoming = new SysUser();
        incoming.setId(11L);
        incoming.setAccount("user11");
        incoming.setPassword(null); // null — should preserve existing

        SysUser existing = new SysUser();
        existing.setId(11L);
        existing.setPassword("$2a$existinghash2");

        SysUser saved = new SysUser();
        saved.setId(11L);
        saved.setPassword("$2a$existinghash2");

        when(sysUserRepository.findById(11L)).thenReturn(Optional.of(existing));
        when(sysUserRepository.save(incoming)).thenReturn(saved);

        SysUser result = sysUserService.updateUser(incoming);

        verify(passwordEncoder, never()).encode(anyString());
        assertThat(incoming.getPassword()).isEqualTo("$2a$existinghash2");
        assertThat(result.getPassword()).isEqualTo("$2a$existinghash2");
    }

    @Test
    void updateUser_encodesNewPassword_whenNonBlank() {
        SysUser incoming = new SysUser();
        incoming.setId(20L);
        incoming.setAccount("user20");
        incoming.setPassword("newpassword");

        SysUser saved = new SysUser();
        saved.setId(20L);
        saved.setPassword("$2a$newencoded");

        when(passwordEncoder.encode("newpassword")).thenReturn("$2a$newencoded");
        when(sysUserRepository.save(incoming)).thenReturn(saved);

        SysUser result = sysUserService.updateUser(incoming);

        verify(passwordEncoder).encode("newpassword");
        // findById must NOT be called — no need to fetch existing user
        verify(sysUserRepository, never()).findById(anyLong());
        assertThat(incoming.getPassword()).isEqualTo("$2a$newencoded");
        assertThat(result.getPassword()).isEqualTo("$2a$newencoded");
    }
}
