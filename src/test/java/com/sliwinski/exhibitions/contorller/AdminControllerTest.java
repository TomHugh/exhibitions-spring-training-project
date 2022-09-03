package com.sliwinski.exhibitions.contorller;

import com.sliwinski.exhibitions.controller.AdminController;
import com.sliwinski.exhibitions.entity.Role;
import com.sliwinski.exhibitions.entity.UserIdNameRole;
import com.sliwinski.exhibitions.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserIdNameRoleService userIdNameRoleService;

    @MockBean
    private ExhibitionService exhibitionService;

    @MockBean
    private OrderService orderService;

    private static final Page<UserIdNameRole> USERS_PAGE = new PageImpl<>(List.of(new UserIdNameRole(1, "user", Role.USER)));


    @Test
    public void getDashboard() throws Exception {
        mockMvc.perform(get("/admin")
                .with(SecurityMockMvcRequestPostProcessors.user("admin").authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("totalExhibitions",
                        "totalUsers",
                        "totalAdmins",
                        "totalTicketPurchases",
                        "profit"));
    }

    @Test
    public void getUsers() throws Exception {
        Mockito.when(userIdNameRoleService.getAllUsers(Mockito.anyInt())).thenReturn(USERS_PAGE);
        mockMvc.perform(get("/admin/users")
                .with(SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

    @Test
    public void signinRedirection() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(redirectedUrl("http://localhost/signin"))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/admin/users"))
                .andExpect(redirectedUrl("http://localhost/signin"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void accessForbidden() throws Exception {
        mockMvc.perform(get("/admin")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/admin/users")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(status().isForbidden());
    }
}
