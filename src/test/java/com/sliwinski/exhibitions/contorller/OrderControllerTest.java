package com.sliwinski.exhibitions.contorller;

import com.sliwinski.exhibitions.controller.OrderController;
import com.sliwinski.exhibitions.dto.OrderDetailsDto;
import com.sliwinski.exhibitions.dto.OrderDto;
import com.sliwinski.exhibitions.entity.Order;
import com.sliwinski.exhibitions.service.AuthService;
import com.sliwinski.exhibitions.service.CustomUserDetailsService;
import com.sliwinski.exhibitions.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private AuthService authService;

    @MockBean
    private ModelMapper modelMapper;

    private static final List<Order> ORDERS_LIST = Collections.emptyList();
    private static final List<OrderDto> ORDERS_DTO_LIST = Collections.emptyList();
    private static final List<OrderDetailsDto> ORDERS_DETAILS_DTO_LIST = Collections.emptyList();
    private static final Page<Order> ORDERS_PAGE = new PageImpl<>(ORDERS_LIST);

    @Test
    public void getUserOrders() throws Exception{
        Mockito.when(orderService.getUserOrders()).thenReturn(ORDERS_LIST);
        mockMvc.perform(get("/orders")
                    .with(SecurityMockMvcRequestPostProcessors.user("user")
                            .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(model().attribute("orders", ORDERS_DTO_LIST))
                .andExpect(status().isOk())
                .andExpect(view().name("user-orders"));
    }

    @Test
    public void getAdminOrders() throws Exception{
        Mockito.when(orderService.getAllOrders(Mockito.anyInt())).thenReturn(ORDERS_PAGE);
        mockMvc.perform(get("/admin/orders")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))))
                .andExpect(model().attribute("orders", ORDERS_DETAILS_DTO_LIST))
                .andExpect(model().attribute("page", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("orders"));
    }

    @Test
    public void createOrder() throws Exception{
        mockMvc.perform(get("/buy")
                .with(SecurityMockMvcRequestPostProcessors.user("user")
                        .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(flash().attribute("class", "alert-success"))
                .andExpect(flash().attributeExists("message"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exhibitions"));
    }

    @Test
    public void signinRedirection() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(redirectedUrl("http://localhost/signin"))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/orders"))
                .andExpect(redirectedUrl("http://localhost/signin"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void accessForbidden() throws Exception {
        mockMvc.perform(get("/admin/orders")
                        .with(SecurityMockMvcRequestPostProcessors.user("user")
                                .authorities(List.of(new SimpleGrantedAuthority("USER")))))
                .andExpect(status().isForbidden());
    }

}
