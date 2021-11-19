package com.zigzag.auction.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Role;
import com.zigzag.auction.repository.RoleRepository;
import com.zigzag.auction.service.RoleService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RoleServiceImplTest {
    private static RoleService roleService;
    private static RoleRepository roleRepository;
    private static Role userRole;
    private static Role adminRole;

    @BeforeAll
    static void beforeAll() {
        roleRepository = mock(RoleRepository.class);
        userRole = new Role(Role.RoleName.ROLE_USER);
        adminRole = new Role(Role.RoleName.ROLE_ADMIN);
        when(roleRepository.findByName(Role.RoleName.ROLE_USER))
                .thenReturn(Optional.of(userRole));
        when(roleRepository.findByName(Role.RoleName.ROLE_ADMIN))
                .thenReturn(Optional.of(adminRole));

        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void add_ok() {
        Role role = new Role(Role.RoleName.ROLE_USER);
        roleService.add(role);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void getRoleByNameUserRole_ok() {
        Role actual = roleService.getRoleByName(Role.RoleName.ROLE_USER);
        assertNotNull(actual);
        assertEquals(Role.RoleName.ROLE_USER, actual.getName());
    }

    @Test
    void getRoleByNameAdminRole_ok() {
        Role actual = roleService.getRoleByName(Role.RoleName.ROLE_ADMIN);
        assertNotNull(actual);
        assertEquals(Role.RoleName.ROLE_ADMIN, actual.getName());
    }

    @Test
    void getRoleByEmptyRole_notOk() {
        assertThrows(DataProcessingException.class, () -> roleService.getRoleByName(null));

        try {
            roleService.getRoleByName(null);
        } catch (DataProcessingException e) {
            String expected = String.format("Role with name %s not found", "null");
            assertEquals(expected, e.getMessage());
        }
    }
}
