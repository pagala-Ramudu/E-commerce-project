package com.ecom.controller;

import static org.mockito.Mockito.when;

import com.ecom.entity.JwtRequest;
import com.ecom.entity.JwtResponse;
import com.ecom.entity.User;
import com.ecom.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@ContextConfiguration(classes = {JwtController.class})
@ExtendWith(SpringExtension.class)
class JwtControllerDiffblueTest {
    @Autowired
    private JwtController jwtController;

    @MockBean
    private JwtService jwtService;

    /**
     * Method under test: {@link JwtController#createJwtToken(JwtRequest)}
     */
    @Test
    void testCreateJwtToken() throws Exception {
        when(jwtService.createJwtToken(Mockito.<JwtRequest>any())).thenReturn(new JwtResponse(new User(), "ABC123"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUserName("janedoe");
        jwtRequest.setUserPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(jwtController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"user\":{\"userName\":null,\"userFirstName\":null,\"userLastName\":null,\"userPassword\":null,\"role\":null},"
                                        + "\"jwtToken\":\"ABC123\"}"));
    }
}

