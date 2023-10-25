package com.ecom.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecom.entity.User;
import com.ecom.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;

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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerDiffblueTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#initRoleAndUser()}
     */
    @Test
    void testInitRoleAndUser() {
        doNothing().when(userService).initRoleAndUser();
        userController.initRoleAndUser();
        verify(userService).initRoleAndUser();
    }

    /**
     * Method under test: {@link UserController#forAdmin()}
     */
    @Test
    void testForAdmin() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/forAdmin");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("This URL is only accessible to the admin"));
    }

    /**
     * Method under test: {@link UserController#registerNewUser(User)}
     */
    @Test
    void testRegisterNewUser() throws Exception {
        User user = new User();
        user.setRole(new HashSet<>());
        user.setUserFirstName("Jane");
        user.setUserLastName("Doe");
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        when(userService.registerNewUser(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setRole(new HashSet<>());
        user2.setUserFirstName("Jane");
        user2.setUserLastName("Doe");
        user2.setUserName("janedoe");
        user2.setUserPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/registerNewUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userName\":\"janedoe\",\"userFirstName\":\"Jane\",\"userLastName\":\"Doe\",\"userPassword\":\"iloveyou\","
                                        + "\"role\":[]}"));
    }

    /**
     * Method under test: {@link UserController#forAdmin()}
     */
    @Test
    void testForAdmin2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/forAdmin", "Uri Vars");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("This URL is only accessible to the admin"));
    }

    /**
     * Method under test: {@link UserController#forUser()}
     */
    @Test
    void testForUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/forUser");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("This URL is only accessible to the user"));
    }

    /**
     * Method under test: {@link UserController#forUser()}
     */
    @Test
    void testForUser2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/forUser");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("This URL is only accessible to the user"));
    }
}

