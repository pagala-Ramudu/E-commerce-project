package com.ecom.controller;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.ecom.entity.OrderInput;
import com.ecom.service.OrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderDetailController.class})
@ExtendWith(SpringExtension.class)
class OrderDetailControllerDiffblueTest {
    @Autowired
    private OrderDetailController orderDetailController;

    @MockBean
    private OrderDetailService orderDetailService;

    /**
     * Method under test: {@link OrderDetailController#placeOrder(boolean, OrderInput)}
     */
    @Test
    void testPlaceOrder() throws Exception {
        doNothing().when(orderDetailService).placeOrder(Mockito.<OrderInput>any(), anyBoolean());

        OrderInput orderInput = new OrderInput();
        orderInput.setAlternateContactNumber("42");
        orderInput.setContactNumber("42");
        orderInput.setFullAddress("42 Main St");
        orderInput.setFullName("Dr Jane Doe");
        orderInput.setOrderProductQuantityList(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(orderInput);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/placeOrder/{isSingleProductCheckout}", true)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(orderDetailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link OrderDetailController#getOrderDetails()}
     */
    @Test
    void testGetOrderDetails() throws Exception {
        when(orderDetailService.getOrderDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getOrderDetails");
        MockMvcBuilders.standaloneSetup(orderDetailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link OrderDetailController#getOrderDetails()}
     */
    @Test
    void testGetOrderDetails2() throws Exception {
        when(orderDetailService.getOrderDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getOrderDetails");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(orderDetailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link OrderDetailController#getAllOrderDetails()}
     */
    @Test
    void testGetAllOrderDetails() throws Exception {
        when(orderDetailService.getAllOrderDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAllOrderDetails");
        MockMvcBuilders.standaloneSetup(orderDetailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link OrderDetailController#getAllOrderDetails()}
     */
    @Test
    void testGetAllOrderDetails2() throws Exception {
        when(orderDetailService.getAllOrderDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAllOrderDetails");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(orderDetailController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

