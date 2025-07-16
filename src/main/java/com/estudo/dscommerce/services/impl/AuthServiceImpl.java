package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.exception.exceptions.ForbiddenException;
import com.estudo.dscommerce.model.User;
import com.estudo.dscommerce.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;

    public AuthServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void validateSelfOrAdmin(Long userId) {
        User user = userService.authenticated();
        if(!user.hasRole("ROLE_ADMIN") && !user.getId().equals(userId)) {
            throw new ForbiddenException("Acess denied");
        }
    }
}
