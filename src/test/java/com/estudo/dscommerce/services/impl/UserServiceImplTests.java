package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.model.User;
import com.estudo.dscommerce.projections.UserDetailsProjection;
import com.estudo.dscommerce.repositories.UserRepository;
import com.estudo.dscommerce.tests.UserDetailsFactory;
import com.estudo.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private String existingUsername, nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() throws Exception{

        existingUsername = "maria@gmail.com";
        nonExistingUsername = "naoexiste@gmail.com";
        user = UserFactory.createCustomClientUser(1l, existingUsername);
        userDetails = UserDetailsFactory.createCustomAdminUser(existingUsername);
    }

    @Test
    public void loadUserByUsernameShouldReturnUserWhenUserNameExists(){

        Mockito.when(userRepository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);

        UserDetails result = userService.loadUserByUsername(existingUsername);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUsername);


    }

    @Test
    public void loadUserByUsernameShouldReturnUsernameNotFoundExceptionWhenUsernameDoesNotExists(){

        Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(UsernameNotFoundException.class, () ->{
           UserDetails result = userService.loadUserByUsername(nonExistingUsername);
        });
    }

}
