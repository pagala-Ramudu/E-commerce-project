package com.ecom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.ecom.entity.Product;
import com.ecom.service.ProductService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
class ProductControllerDiffblueTest {
    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    /**
     * Method under test: {@link ProductController#uplodImage(MultipartFile[])}
     */
    @Test
    void testUplodImage() throws IOException {
        assertEquals(1, productController.uplodImage(
                        new MultipartFile[]{new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))})
                .size());
    }

    /**
     * Method under test: {@link ProductController#addNewProduct(Product, MultipartFile[])}
     */
    @Test
    void testAddNewProduct() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/addNewProduct");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("file", String.valueOf(new MultipartFile[]{null}));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    /**
     * Method under test: {@link ProductController#getAllProducts(int, String)}
     */
    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts(anyInt(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/getAllProducts");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("pageNumber", String.valueOf(1))
                .param("searchKey", "foo");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ProductController#getProductDetailsById(Integer)}
     */
    @Test
    void testGetProductDetailsById() throws Exception {
        Product product = new Product();
        product.setProductActualPrice(10.0d);
        product.setProductDescription("Product Description");
        product.setProductDiscountedPrice(10.0d);
        product.setProductId(1);
        product.setProductImages(new HashSet<>());
        product.setProductName("Product Name");
        when(productService.getProductDetailsById(Mockito.<Integer>any())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getProductDetailsById/{productId}",
                1);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productId\":1,\"productName\":\"Product Name\",\"productDescription\":\"Product Description\",\"productDisco"
                                        + "untedPrice\":10.0,\"productActualPrice\":10.0,\"productImages\":[]}"));
    }

    /**
     * Method under test: {@link ProductController#getProductDetails(boolean, Integer)}
     */
    @Test
    void testGetProductDetails() throws Exception {
        when(productService.getProductDetails(anyBoolean(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getProductDetails/{isSingeProductCheckout}/{productId}", true, 1);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ProductController#getProductDetails(boolean, Integer)}
     */
    @Test
    void testGetProductDetails2() throws Exception {
        when(productService.getProductDetails(anyBoolean(), Mockito.<Integer>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/getProductDetails/{isSingeProductCheckout}/{productId}", true, 1);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ProductController#deleteProductDetailes(Integer)}
     */
    @Test
    void testDeleteProductDetailes() throws Exception {
        doNothing().when(productService).deleteProductDetails(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteProductDetails/{productId}",
                1);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link ProductController#deleteProductDetailes(Integer)}
     */
    @Test
    void testDeleteProductDetailes2() throws Exception {
        doNothing().when(productService).deleteProductDetails(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteProductDetails/{productId}",
                1);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

