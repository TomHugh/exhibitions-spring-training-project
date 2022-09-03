package com.sliwinski.exhibitions.contorller;

import com.sliwinski.exhibitions.controller.AuthController;
import com.sliwinski.exhibitions.service.CustomUserDetailsService;
import com.sliwinski.exhibitions.service.UserService;
import com.sliwinski.exhibitions.service.validator.Validate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private Validate validate;

    @Test
    public void getSignin() throws Exception {
        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"));
    }

    @Test
    public void getRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void postRegister() throws Exception {
        mockMvc.perform(post("/register"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void getNewAdmin() throws Exception {
        mockMvc.perform(get("/admin/new-admin")
                    .with(SecurityMockMvcRequestPostProcessors.user("admin").authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(status().isOk())
                .andExpect(view().name("new-admin"));
    }

    @Test
    public void postNewAdmin() throws Exception {
        mockMvc.perform(post("/admin/new-admin")
                    .with(SecurityMockMvcRequestPostProcessors.user("admin").authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    public void signinRedirection() throws Exception {
        mockMvc.perform(get("/admin/new-admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/signin"));
    }

    @Test
    public void accessForbidden() throws Exception {
        mockMvc.perform(get("/admin/new-admin")
                    .with(SecurityMockMvcRequestPostProcessors.user("user")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(status().isForbidden());
    }

}
