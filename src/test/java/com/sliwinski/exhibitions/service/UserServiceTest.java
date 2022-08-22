package com.sliwinski.exhibitions.service;

import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.User;
import com.sliwinski.exhibitions.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    private static final User USER = new User(1, "User", "password", Role.USER);
    private static final User ADMIN = new User(2, "Admin", "password", Role.ADMIN);


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserQuantity() {
        Mockito.when(userRepository.countByRole(Mockito.any(Role.class))).thenReturn(10L);
        assertEquals(10L, userService.getUserQuantity());
    }

    @Test
    public void getAdminQuantity() {
        Mockito.when(userRepository.countByRole(Mockito.any(Role.class))).thenReturn(10L);
        assertEquals(10L, userService.getAdminQuantity());
    }

    @Test
    public void createUser() {
        userService.createUser("User", "password");
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        assertEquals("User", user.getUsername());
        assertEquals(passwordEncoder.encode("password"), user.getPassword());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    public void createAdmin() {
        userService.createAdmin("Admin", "password");
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();

        assertEquals("Admin", user.getUsername());
        assertEquals(passwordEncoder.encode("password"), user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());

    }

}
