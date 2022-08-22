package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.UserIdNameRole;
import com.sliwinski.exhibitions.repository.UserIdNameRoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserIdNameRoleServiceTest {
    private static final UserIdNameRole USER1 = new UserIdNameRole(1, "User 1", Role.USER);
    private static final UserIdNameRole USER2 = new UserIdNameRole(2, "User 2", Role.USER);
    private Page<UserIdNameRole> pageUsers = new PageImpl<>(List.of(USER1, USER2));

    @Mock
    private UserIdNameRoleRepository userIdNameRoleRepository;

    @InjectMocks
    private UserIdNameRoleService userIdNameRoleService;

    @Test
    public void getAllUsers() {
        Mockito.when(userIdNameRoleRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(pageUsers);
        assertEquals(pageUsers, userIdNameRoleService.getAllUsers(1));
    }
}
