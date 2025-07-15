package com.estudo.dscommerce.dto.response;

import com.estudo.dscommerce.model.User;

public class ClientResponseDTO {

    private Long id;
    private String name;

    public ClientResponseDTO(){

    }

    public ClientResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientResponseDTO(User user) {
        id = user.getId();
        name = user.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
