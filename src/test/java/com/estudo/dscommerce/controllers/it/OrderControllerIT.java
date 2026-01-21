package com.estudo.dscommerce.controllers.it;

import com.estudo.dscommerce.dto.request.OrderRequestDTO;
import com.estudo.dscommerce.model.*;
import com.estudo.dscommerce.tests.OrderFactory;
import com.estudo.dscommerce.tests.ProductFactory;
import com.estudo.dscommerce.tests.TokenUtil;
import com.estudo.dscommerce.tests.UserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarantlr4.runtime.misc.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String clientToken, adminToken, invalidToken;

    private String clientUsername, clientPassword, adminUsername, adminPassword;

    private Long existingOrderId, nonExistingOrderId;

    private Order order;
    private OrderRequestDTO orderRequestDTO;
    private User user;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() throws Exception{

        clientUsername = "alex@gmail.com";
        clientPassword = "123456";

        adminUsername = "maria@gmail.com";
        adminPassword = "123456";

        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        invalidToken = adminToken + "dwras";

        existingOrderId = 1L;
        nonExistingOrderId = 100L;

        user = UserFactory.createClientUser();
        order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, user, null);
        product = ProductFactory.createProduct();
        orderItem = new OrderItem(order, product, 2, 10.0);
        order.getItems().add(orderItem);

    }

    @Test
    public void findByIdShouldReturnExistingOrderWhenLoggedAdmin() throws Exception{

        ResultActions result = mockMvc.perform(get("/orders/{id}", existingOrderId)
                .header("Authorization", "Bearer " + adminToken)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingOrderId));


    }



}
