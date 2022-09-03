package com.sliwinski.exhibitions.contorller;

import com.sliwinski.exhibitions.controller.HomeController;
import com.sliwinski.exhibitions.dto.ExhibitionDetailsDto;
import com.sliwinski.exhibitions.dto.ExhibitionDto;
import com.sliwinski.exhibitions.entity.Exhibition;
import com.sliwinski.exhibitions.entity.Location;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.CustomUserDetailsService;
import com.sliwinski.exhibitions.service.ExhibitionService;
import com.sliwinski.exhibitions.service.utility.Cart;
import com.sliwinski.exhibitions.service.utility.Search;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExhibitionService exhibitionService;

    @MockBean
    private AuthService authService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;


    private static final Location LOCATION = new Location(1, "TEST LOCATION");
    private static final ExhibitionDetailsDto EXHIBITION_DETAILS_DTO = new ExhibitionDetailsDto(
            1,
            "test",
            LocalDate.of(2022, 2, 1),
            LocalDate.of(2022,3,1),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            new ArrayList<>(Collections.singleton(LOCATION)),
            34.0);
    private static final ExhibitionDto EXHIBITION_DTO = new ExhibitionDto(
            1,
            "test",
            LocalDate.of(2022, 2, 1),
            LocalDate.of(2022,3,1),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            34.0);
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
    private static final Cart CART = new Cart();

    @Test
    public void getHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exhibitions?page=1"));
    }

    @Test
    public void getExhibitions() throws Exception{
        SEARCH.setTo(LocalDate.now());
        Mockito.when(exhibitionService.searchAndSortActiveExhibitions(Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.anyInt(),
                Mockito.any(Sort.Direction.class),
                Mockito.anyString())).thenReturn(EXHIBITIONS_PAGE);
        mockMvc.perform(get("/exhibitions")
                        .sessionAttr("search", SEARCH))
                .andExpect(model().attribute("totalPages", EXHIBITIONS_PAGE.getTotalPages()))
                .andExpect(model().attribute("search", SEARCH))
                .andExpect(model().attribute("exhibitions", EXHIBITIONS_PAGE.getContent()))
                .andExpect(model().attribute("page", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void postExhibitions() throws Exception {
        SEARCH.setTo(LocalDate.now());
        mockMvc.perform(post("/exhibitions")
                        .sessionAttr("search", SEARCH))
                .andExpect(flash().attribute("search", SEARCH))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exhibitions"));
    }

    @Test
    public void getExhibition() throws Exception {
        Mockito.when(exhibitionService.getExhibition(Mockito.anyInt())).thenReturn(EXHIBITION);
        Mockito.when(modelMapper.map(Mockito.any(Exhibition.class), eq(ExhibitionDetailsDto.class))).thenReturn(EXHIBITION_DETAILS_DTO);
        mockMvc.perform(get("/exhibitions/1"))
                .andExpect(model().attribute("exhibition", EXHIBITION_DETAILS_DTO))
                .andExpect(status().isOk())
                .andExpect(view().name("exhibition"));
    }

    @Test
    public void addToCart() throws Exception{
        Mockito.when(exhibitionService.findExhibition(Mockito.anyInt())).thenReturn(EXHIBITION);
        Mockito.when(modelMapper.map(Mockito.any(Exhibition.class), eq(ExhibitionDto.class))).thenReturn(EXHIBITION_DTO);
        mockMvc.perform(get("/cart/add")
                .with(SecurityMockMvcRequestPostProcessors.user("user")
                        .authorities(List.of(new SimpleGrantedAuthority("USER"))))
                        .param("id","1")
                        .param("quantity", "1"))
                .andExpect(flash().attribute("class", "alert-success"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    public void removeFromCart() throws Exception{
        CART.addItem(EXHIBITION_DTO, 1);
        mockMvc.perform(get("/cart/remove")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER"))))
                        .param("id","1")
                        .sessionAttr("cart", CART))
                .andExpect(flash().attribute("class", "alert-success"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    public void updateCart() throws Exception{
        mockMvc.perform(post("/cart/update")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(flash().attribute("class", "alert-success"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    public void getCart() throws Exception{
        mockMvc.perform(get("/cart")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(model().attribute("totalQuantity", 0))
                .andExpect(model().attribute("totalPrice", 0.0));
    }

    @Test
    public void signinRedirection() throws Exception{
        mockMvc.perform(get("/cart/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/signin"));

    }
}
