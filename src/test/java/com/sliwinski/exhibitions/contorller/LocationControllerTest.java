package com.sliwinski.exhibitions.contorller;

import com.sliwinski.exhibitions.controller.LocationController;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.service.CustomUserDetailsService;
import com.sliwinski.exhibitions.service.LocationService;
import com.sliwinski.exhibitions.service.validator.Validate;
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

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private Validate validate;

    private static final Page<Location> LOCATIONS_PAGE = new PageImpl<>(Collections.emptyList());

    @Test
    public void getLocations() throws Exception{
        Mockito.when(locationService.getAllLocations(Mockito.anyInt())).thenReturn(LOCATIONS_PAGE);
        mockMvc.perform(get("/admin/locations")
                    .with(SecurityMockMvcRequestPostProcessors.user("admin")
                            .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(model().attribute("page", LOCATIONS_PAGE.getTotalPages()))
                .andExpect(model().attribute("locations", LOCATIONS_PAGE.getContent()))
                .andExpect(status().isOk())
                .andExpect(view().name("locations"));
    }

    @Test
    public void getAddLocation() throws Exception{
        mockMvc.perform(get("/admin/locations/new")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(status().isOk())
                .andExpect(view().name("new-location"));
    }

    @Test
    public void postAddLocation() throws Exception{
        mockMvc.perform(post("/admin/locations/new")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(List.of(new SimpleGrantedAuthority("ADMIN"))))
                        .param("locationName", "Test location"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/locations"));
    }

    @Test
    public void signinRedirection() throws Exception{
        mockMvc.perform(get("/admin/locations"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/signin"));
    }

    @Test
    public void accessForbidden() throws Exception {
        mockMvc.perform(get("/admin/locations")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(status().isForbidden());
    }
}
