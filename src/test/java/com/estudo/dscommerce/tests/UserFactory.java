package com.estudo.dscommerce.tests;

import com.estudo.dscommerce.model.Role;
import com.estudo.dscommerce.model.User;

import java.time.LocalDate;

public class UserFactory {

    public static User createClientUser(){
        User user = new User(1L, "maria", "maria@gmail.com", "98888889", LocalDate.parse("2001-07-25"), "$2a$10$BZEayVp6X1Ry93e44/Rnze0hpK5J3ThbAdUm2OzH.GSWjA4zmtGHW");
        user.addRole(new Role(1l, "ROLE_CLIENT"));
        return user;
    }

    public static User createCustomClientUser(Long id, String username){
        User user = new User(id, "maria", username, "98888889", LocalDate.parse("2001-07-25"), "$2a$10$BZEayVp6X1Ry93e44/Rnze0hpK5J3ThbAdUm2OzH.GSWjA4zmtGHW");
        user.addRole(new Role(1l, "ROLE_CLIENT"));
        return user;
    }

    public static User createAdminUser(){
        User user = new User(2L, "alex", "alex@gmail.com", "977777779", LocalDate.parse("1987-12-13"), "$2a$10$BZEayVp6X1Ry93e44/Rnze0hpK5J3ThbAdUm2OzH.GSWjA4zmtGHW");
        user.addRole(new Role(2l, "ROLE_ADMIN"));
        return user;
    }

    public static User createCustomAdminUser(Long id, String username){
        User user = new User(id, "alex", username, "977777779", LocalDate.parse("1987-12-13"), "$2a$10$BZEayVp6X1Ry93e44/Rnze0hpK5J3ThbAdUm2OzH.GSWjA4zmtGHW");
        user.addRole(new Role(2l, "ROLE_ADMIN"));
        return user;
    }



}
