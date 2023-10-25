package com.ecom.controller;

import static org.mockito.Mockito.when;

import com.ecom.entity.Role;
import com.ecom.service.RoleService;
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

@ContextConfiguration(classes = {RoleController.class})
@ExtendWith(SpringExtension.class)
class RoleControllerDiffblueTest {
    @Autowired
    private RoleController roleController;

    @MockBean
    private RoleService roleService;

    /**
     * Method under test: {@link RoleController#createNewRole(Role)}
     */
    @Test
    void testCreateNewRole() throws Exception {
        Role role = new Role();
        role.setRoleDescription("Role Description");
        role.setRoleName("Role Name");
        when(roleService.createNewRole(Mockito.<Role>any())).thenReturn(role);

        Role role2 = new Role();
        role2.setRoleDescription("Role Description");
        role2.setRoleName("Role Name");
        String content = (new ObjectMapper()).writeValueAsString(role2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createNewRole")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(roleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"roleName\":\"Role Name\",\"roleDescription\":\"Role Description\"}"));
    }
}

