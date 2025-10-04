package com.estudo.dscommerce.services.impl;

import com.estudo.dscommerce.dto.response.UserResponseDTO;
import com.estudo.dscommerce.model.Role;
import com.estudo.dscommerce.model.User;
import com.estudo.dscommerce.projections.UserDetailsProjection;
import com.estudo.dscommerce.repositories.UserRepository;
import com.estudo.dscommerce.util.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private CustomUserUtil customUserUtil;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.
                searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email not found");
        }
        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.
                    getAuthority()));
        }
        return user;
    }

    protected User authenticated(){
        try{
            String username = customUserUtil.getLoggedUsername();
            return userRepository.findByEmail(username).get();
        }
        catch (Exception e){
            throw new UsernameNotFoundException("Email not found");
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getMe(){
        User user = authenticated();
        return new UserResponseDTO(user);
    }

}
