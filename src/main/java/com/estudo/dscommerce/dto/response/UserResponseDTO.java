package com.estudo.dscommerce.dto.response;


import com.estudo.dscommerce.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;

    List<String> roles = new ArrayList<>();

    public UserResponseDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        phone = user.getPhone();
        birthDate = user.getBirthDate();
        for(GrantedAuthority grantedAuthority : user.getRoles()){
            roles.add(grantedAuthority.getAuthority());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<String> getRoles() {
        return roles;
    }
}
