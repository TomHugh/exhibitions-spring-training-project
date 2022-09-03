package com.sliwinski.exhibitions.contorller;

import com.sliwinski.exhibitions.controller.ExhibitionController;
import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.service.CustomUserDetailsService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.LocationService;
import com.sliwinski.exhibitions.service.OrderService;
import com.sliwinski.exhibitions.service.utility.DatesLocations;
import com.sliwinski.exhibitions.service.utility.Search;
import com.sliwinski.exhibitions.service.validator.Validate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ExhibitionController.class)
public class ExhibitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExhibitionService exhibitionService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private Validate validate;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private static final Location LOCATION = new Location(1, "TEST LOCATION");
    private static final Exhibition EXHIBITION  = new Exhibition(
            1,
            "test",
            LocalDate.of(2022, 2, 1),
            LocalDate.of(2022,3,1),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            34.0,
            true,
            new ArrayList<>(Collections.singleton(LOCATION)));
    private static final Page<Exhibition> EXHIBITIONS_PAGE = new PageImpl<>(List.of(EXHIBITION));
    private static final Search SEARCH = new Search();
    private static final DatesLocations DATES_LOCATIONS = new DatesLocations();




    @Test
    public void getExhibitions() throws Exception{
        SEARCH.setTo(LocalDate.now());
        Mockito.when(exhibitionService.searchAndSortExhibitions(Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.anyInt(),
                Mockito.any(Sort.Direction.class),
                Mockito.anyString())).thenReturn(EXHIBITIONS_PAGE);
        mockMvc.perform(get("/admin/exhibitions")
                    .with(SecurityMockMvcRequestPostProcessors.user("admin")
                            .authorities(List.of(new SimpleGrantedAuthority("ADMIN"))))
                        .sessionAttr("search", SEARCH))
                .andExpect(model().attribute("totalPages", EXHIBITIONS_PAGE.getTotalPages()))
                .andExpect(model().attribute("search", SEARCH))
                .andExpect(model().attribute("exhibitions", EXHIBITIONS_PAGE.getContent()))
                .andExpect(model().attribute("page", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("exhibitions"));
    }

    @Test
    public void postExhibitions() throws Exception {
        SEARCH.setTo(LocalDate.now());
        mockMvc.perform(post("/admin/exhibitions")
                .with(SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN"))))
                        .sessionAttr("search", SEARCH))
                .andExpect(flash().attribute("search", SEARCH))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/exhibitions"));

    }

    @Test
    public void getExhibition() throws Exception {
        Mockito.when(exhibitionService.getExhibition(Mockito.anyInt())).thenReturn(EXHIBITION);
        mockMvc.perform(get("/admin/exhibitions/1")
                .with(SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(model().attribute("exhibition", EXHIBITION))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-exhibition"));
    }

    @Test
    public void getCheckLocations() throws Exception {
        mockMvc.perform(get("/admin/exhibitions/new/check-locations")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(model().attributeExists("datesLocations"))
                .andExpect(status().isOk())
                .andExpect(view().name("check-locations"));
    }

    @Test
    public void postCheckLocations() throws Exception {
        DATES_LOCATIONS.setStartDate(LocalDate.now());
        DATES_LOCATIONS.setEndDate(LocalDate.now());
        Mockito.when(locationService.checkAvailability(Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class))).thenReturn(Collections.singletonList(LOCATION));
        mockMvc.perform(post("/admin/exhibitions/new/check-locations")
                    .with(SecurityMockMvcRequestPostProcessors.user("admin")
                            .authorities(List.of(new SimpleGrantedAuthority("ADMIN"))))
                .flashAttr("datesLocations", DATES_LOCATIONS))
                .andExpect(flash().attribute("datesLocations", DATES_LOCATIONS))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/exhibitions/new/details"));
    }

    @Test
    public void getDetails() throws Exception {
        DATES_LOCATIONS.setStartDate(LocalDate.now());
        DATES_LOCATIONS.setEndDate(LocalDate.now());
        DATES_LOCATIONS.setLocations(Collections.singletonList(LOCATION));
        mockMvc.perform(get("/admin/exhibitions/new/details")
                .with(SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN"))))
                .flashAttr("datesLocations", DATES_LOCATIONS))
                .andExpect(model().attributeExists("exhibition"))
                .andExpect(model().attribute("locations", List.of(LOCATION)))
                .andExpect(status().isOk())
                .andExpect(view().name("details"));
    }

    @Test
    public void postDetails() throws Exception {
        mockMvc.perform(post("/admin/exhibitions/new/details")
                .with(SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(flash().attribute("class", "alert-success"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    public void addExhibition() throws Exception {
        mockMvc.perform(get("/admin/exhibitions/new")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/exhibitions/new/check-locations"));
    }

    @Test
    public void getCancelExhibition() throws Exception {
        Mockito.when(orderService.countByExhibitionId(Mockito.anyInt())).thenReturn(10L);
        mockMvc.perform(get("/admin/exhibitions/cancel/1")
                .with(SecurityMockMvcRequestPostProcessors.user("admin")
                        .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(model().attribute("orders", 10L))
                .andExpect(status().isOk())
                .andExpect(view().name("cancel-exhibition"));

    }

    @Test
    public void postCancelExhibition() throws Exception {
        mockMvc.perform(post("/admin/exhibitions/cancel/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(flash().attribute("class", "alert-success"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/exhibitions"));
    }

    @Test
    public void signinRedirection() throws Exception{
        mockMvc.perform(get("/admin/exhibitions"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/signin"));
    }

    @Test
    public void accessForbidden() throws Exception {
        mockMvc.perform(get("/admin/exhibitions")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(status().isForbidden());
    }

}
