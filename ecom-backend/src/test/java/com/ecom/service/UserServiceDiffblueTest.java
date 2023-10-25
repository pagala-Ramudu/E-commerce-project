package com.ecom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecom.dao.RoleDao;
import com.ecom.dao.UserDao;
import com.ecom.entity.Role;
import com.ecom.entity.User;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceDiffblueTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleDao roleDao;

    @MockBean
    private UserDao userDao;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#initRoleAndUser()}
     */
    @Test
    void testInitRoleAndUser() {
        Role role = new Role();
        role.setRoleDescription("Role Description");
        role.setRoleName("Role Name");
        when(roleDao.save(Mockito.<Role>any())).thenReturn(role);

        User user = new User();
        user.setRole(new HashSet<>());
        user.setUserFirstName("Jane");
        user.setUserLastName("Doe");
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        when(userDao.save(Mockito.<User>any())).thenReturn(user);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        userService.initRoleAndUser();
        verify(roleDao, atLeast(1)).save(Mockito.<Role>any());
        verify(userDao).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link UserService#registerNewUser(User)}
     */
    @Test
    void testRegisterNewUser() {
        Role role = new Role();
        role.setRoleDescription("Role Description");
        role.setRoleName("Role Name");
        Optional<Role> ofResult = Optional.of(role);
        when(roleDao.findById(Mockito.<String>any())).thenReturn(ofResult);

        User user = new User();
        user.setRole(new HashSet<>());
        user.setUserFirstName("Jane");
        user.setUserLastName("Doe");
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        when(userDao.save(Mockito.<User>any())).thenReturn(user);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        User user2 = new User();
        user2.setRole(new HashSet<>());
        user2.setUserFirstName("Jane");
        user2.setUserLastName("Doe");
        user2.setUserName("janedoe");
        user2.setUserPassword("iloveyou");
        assertSame(user, userService.registerNewUser(user2));
        verify(roleDao).findById(Mockito.<String>any());
        verify(userDao).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals(1, user2.getRole().size());
        assertEquals("secret", user2.getUserPassword());
    }

    /**
     * Method under test: {@link UserService#getEncodedPassword(String)}
     */
    @Test
    void testGetEncodedPassword() {
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        assertEquals("secret", userService.getEncodedPassword("iloveyou"));
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }
}

