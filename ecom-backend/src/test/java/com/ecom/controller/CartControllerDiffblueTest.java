package com.ecom.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.ecom.entity.Cart;
import com.ecom.entity.Product;
import com.ecom.entity.User;
import com.ecom.service.CartService;

import java.util.ArrayList;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
class CartControllerDiffblueTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    /**
     * Method under test: {@link CartController#addTocart(Integer)}
     */
    @Test
    void testAddTocart() throws Exception {
        Product product = new Product();
        product.setProductActualPrice(10.0d);
        product.setProductDescription("Product Description");
        product.setProductDiscountedPrice(10.0d);
        product.setProductId(1);
        product.setProductImages(new HashSet<>());
        product.setProductName("Product Name");

        User user = new User();
        user.setRole(new HashSet<>());
        user.setUserFirstName("Jane");
        user.setUserLastName("Doe");
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");

        Cart cart = new Cart();
        cart.setCartId(1);
        cart.setProduct(product);
        cart.setUser(user);
        when(cartService.addToCart(Mockito.<Integer>any())).thenReturn(cart);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/addToCart/{productId}", 1);
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"cartId\":1,\"product\":{\"productId\":1,\"productName\":\"Product Name\",\"productDescription\":\"Product"
                                        + " Description\",\"productDiscountedPrice\":10.0,\"productActualPrice\":10.0,\"productImages\":[]},\"user\":{"
                                        + "\"userName\":\"janedoe\",\"userFirstName\":\"Jane\",\"userLastName\":\"Doe\",\"userPassword\":\"iloveyou\",\"role\":[]"
                                        + "}}"));
    }

    /**
     * Method under test: {@link CartController#deleteCartItem(Integer)}
     */
    @Test
    void testDeleteCartItem() throws Exception {
        doNothing().when(cartService).deleteCartItem(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteCartItem/{cartId}", 1);
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CartController#deleteCartItem(Integer)}
     */
    @Test
    void testDeleteCartItem2() throws Exception {
        doNothing().when(cartService).deleteCartItem(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteCartItem/{cartId}", 1);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CartController#getCartDetails()}
     */
    @Test
    void testGetCartDetails() throws Exception {
        when(cartService.getCartDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getCartDetails");
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CartController#getCartDetails()}
     */
    @Test
    void testGetCartDetails2() throws Exception {
        when(cartService.getCartDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getCartDetails");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

