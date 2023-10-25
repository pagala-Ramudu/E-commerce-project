package com.ecom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecom.dao.CartDao;
import com.ecom.dao.ProductDao;
import com.ecom.dao.UserDao;
import com.ecom.entity.Cart;
import com.ecom.entity.Product;
import com.ecom.entity.User;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
class ProductServiceDiffblueTest {
    @MockBean
    private CartDao cartDao;

    @MockBean
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @MockBean
    private UserDao userDao;

    /**
     * Method under test: {@link ProductService#addNewProduct(Product)}
     */
    @Test
    void testAddNewProduct() {
        Product product = new Product();
        product.setProductActualPrice(10.0d);
        product.setProductDescription("Product Description");
        product.setProductDiscountedPrice(10.0d);
        product.setProductId(1);
        product.setProductImages(new HashSet<>());
        product.setProductName("Product Name");
        when(productDao.save(Mockito.<Product>any())).thenReturn(product);

        Product product2 = new Product();
        product2.setProductActualPrice(10.0d);
        product2.setProductDescription("Product Description");
        product2.setProductDiscountedPrice(10.0d);
        product2.setProductId(1);
        product2.setProductImages(new HashSet<>());
        product2.setProductName("Product Name");
        assertSame(product, productService.addNewProduct(product2));
        verify(productDao).save(Mockito.<Product>any());
    }

    /**
     * Method under test: {@link ProductService#getAllProducts(int, String)}
     */
    @Test
    void testGetAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any())).thenReturn(productList);
        List<Product> actualAllProducts = productService.getAllProducts(10, "Search Key");
        assertSame(productList, actualAllProducts);
        assertTrue(actualAllProducts.isEmpty());
        verify(productDao).findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link ProductService#getAllProducts(int, String)}
     */
    @Test
    void testGetAllProducts2() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productDao.findAll(Mockito.<Pageable>any())).thenReturn(productList);
        List<Product> actualAllProducts = productService.getAllProducts(10, "");
        assertSame(productList, actualAllProducts);
        assertTrue(actualAllProducts.isEmpty());
        verify(productDao).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link ProductService#deleteProductDetails(Integer)}
     */
    @Test
    void testDeleteProductDetails() {
        doNothing().when(productDao).deleteById(Mockito.<Integer>any());
        productService.deleteProductDetails(1);
        verify(productDao).deleteById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ProductService#getProductDetailsById(Integer)}
     */
    @Test
    void testGetProductDetailsById() {
        Product product = new Product();
        product.setProductActualPrice(10.0d);
        product.setProductDescription("Product Description");
        product.setProductDiscountedPrice(10.0d);
        product.setProductId(1);
        product.setProductImages(new HashSet<>());
        product.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product);
        when(productDao.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        assertSame(product, productService.getProductDetailsById(1));
        verify(productDao).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ProductService#getProductDetails(boolean, Integer)}
     */
    @Test
    void testGetProductDetails() {
        Product product = new Product();
        product.setProductActualPrice(10.0d);
        product.setProductDescription("Product Description");
        product.setProductDiscountedPrice(10.0d);
        product.setProductId(1);
        product.setProductImages(new HashSet<>());
        product.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product);
        when(productDao.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        assertEquals(1, productService.getProductDetails(true, 1).size());
        verify(productDao).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link ProductService#getProductDetails(boolean, Integer)}
     */
    @Test
    void testGetProductDetails2() {
        when(cartDao.findByUser(Mockito.<User>any())).thenReturn(new ArrayList<>());

        Product product = new Product();
        product.setProductActualPrice(10.0d);
        product.setProductDescription("Product Description");
        product.setProductDiscountedPrice(10.0d);
        product.setProductId(1);
        product.setProductImages(new HashSet<>());
        product.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product);
        when(productDao.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        User user = new User();
        user.setRole(new HashSet<>());
        user.setUserFirstName("Jane");
        user.setUserLastName("Doe");
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        Optional<User> ofResult2 = Optional.of(user);
        when(userDao.findById(Mockito.<String>any())).thenReturn(ofResult2);
        assertTrue(productService.getProductDetails(false, 1).isEmpty());
        verify(cartDao).findByUser(Mockito.<User>any());
        verify(userDao).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ProductService#getProductDetails(boolean, Integer)}
     */
    @Test
    void testGetProductDetails3() {
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

        ArrayList<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        when(cartDao.findByUser(Mockito.<User>any())).thenReturn(cartList);

        Product product2 = new Product();
        product2.setProductActualPrice(10.0d);
        product2.setProductDescription("Product Description");
        product2.setProductDiscountedPrice(10.0d);
        product2.setProductId(1);
        product2.setProductImages(new HashSet<>());
        product2.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product2);
        when(productDao.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setRole(new HashSet<>());
        user2.setUserFirstName("Jane");
        user2.setUserLastName("Doe");
        user2.setUserName("janedoe");
        user2.setUserPassword("iloveyou");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userDao.findById(Mockito.<String>any())).thenReturn(ofResult2);
        assertEquals(1, productService.getProductDetails(false, 1).size());
        verify(cartDao).findByUser(Mockito.<User>any());
        verify(userDao).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ProductService#getProductDetails(boolean, Integer)}
     */
    @Test
    void testGetProductDetails4() {
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

        Product product2 = new Product();
        product2.setProductActualPrice(0.5d);
        product2.setProductDescription("42");
        product2.setProductDiscountedPrice(0.5d);
        product2.setProductId(2);
        product2.setProductImages(new HashSet<>());
        product2.setProductName("42");

        User user2 = new User();
        user2.setRole(new HashSet<>());
        user2.setUserFirstName("John");
        user2.setUserLastName("Smith");
        user2.setUserName("User Name");
        user2.setUserPassword("User Password");

        Cart cart2 = new Cart();
        cart2.setCartId(2);
        cart2.setProduct(product2);
        cart2.setUser(user2);

        ArrayList<Cart> cartList = new ArrayList<>();
        cartList.add(cart2);
        cartList.add(cart);
        when(cartDao.findByUser(Mockito.<User>any())).thenReturn(cartList);

        Product product3 = new Product();
        product3.setProductActualPrice(10.0d);
        product3.setProductDescription("Product Description");
        product3.setProductDiscountedPrice(10.0d);
        product3.setProductId(1);
        product3.setProductImages(new HashSet<>());
        product3.setProductName("Product Name");
        Optional<Product> ofResult = Optional.of(product3);
        when(productDao.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        User user3 = new User();
        user3.setRole(new HashSet<>());
        user3.setUserFirstName("Jane");
        user3.setUserLastName("Doe");
        user3.setUserName("janedoe");
        user3.setUserPassword("iloveyou");
        Optional<User> ofResult2 = Optional.of(user3);
        when(userDao.findById(Mockito.<String>any())).thenReturn(ofResult2);
        assertEquals(2, productService.getProductDetails(false, 1).size());
        verify(cartDao).findByUser(Mockito.<User>any());
        verify(userDao).findById(Mockito.<String>any());
    }
}

