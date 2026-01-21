package com.estudo.dscommerce.controllers.it;


import com.estudo.dscommerce.dto.request.ProductRequestDTO;
import com.estudo.dscommerce.model.Category;
import com.estudo.dscommerce.model.Product;
import com.estudo.dscommerce.tests.TokenUtil;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;


    private String productName;
    private String clientToken, adminToken, invalidToken;

    private String clientUsername, clientPassword, adminUsername, adminPassword;

    private ProductRequestDTO productRequestDTO;
    private Product product;

    private Long existingProductId, notExistingProductId, dependentProductId;


    @BeforeEach
    void setUp() throws Exception {

        clientUsername = "alex@gmail.com";
        clientPassword = "123456";

        adminUsername = "maria@gmail.com";
        adminPassword = "123456";

        productName = "Macbook";

        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

        Category category = new Category(2L, "Eletronico");
        product = new Product(null, "Tablet", "tablet muito ruim", 50.0, "http://imagem.com");
        product.getCategories().add(category);
        productRequestDTO = new ProductRequestDTO(product);

        existingProductId = 1L;
        notExistingProductId = Long.MAX_VALUE;
        dependentProductId = 3L;

    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {

        ResultActions result = mockMvc.perform(get("/products?name={productName}", productName).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(3L));
        result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[0].price").value("1250.0"));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));

    }

    @Test
    public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {

        ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(1L));
        result.andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"));
        result.andExpect(jsonPath("$.content[0].price").value("90.5"));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));

    }

    @Test
    @WithMockUser(username = "maria@gmail.com", roles = {"ADMIN"})
    public void insertShouldReturnProductDTOCreatedWhenLoggedAdm() throws Exception{

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc.perform(post("/products").header("Authorization", "Bearer " + adminToken).content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").value("Tablet"));
        result.andExpect(jsonPath("$.price").value(50.0));
        result.andExpect(jsonPath("$.categories[0].id").value(2L));

    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidName() throws Exception{

        product.setName("ta");
        productRequestDTO = new ProductRequestDTO(product);

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc
                .perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidDescription() throws Exception {

        product.setDescription("kd");
        productRequestDTO = new ProductRequestDTO(product);

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc
                .perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsNegative() throws Exception{

        product.setPrice(-50.0);
        productRequestDTO = new ProductRequestDTO(product);

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc
                .perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsZero() throws Exception{

        product.setPrice(0.0);
        productRequestDTO = new ProductRequestDTO(product);

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc
                .perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenProductItNotHaveCategory() throws Exception{

        product.getCategories().clear();
        productRequestDTO = new ProductRequestDTO(product);

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc
                .perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnForbiddenWhenClientLogged() throws Exception{

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc
                .perform(post("/products")
                        .header("Authorization", "Bearer " + clientToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());

    }

    @Test
    public void insertShouldReturnUnauthorizedWhenUserIsNotLogged() throws Exception{

        String jsonBody = objectMapper.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc
                .perform(post("/products")
                        .header("Authorization", "Bearer " + invalidToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteShouldReturnNothingWhenIdExistsAndAdminLogged() throws Exception{

        ResultActions result = mockMvc
                .perform(delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());

    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdNonExisting() throws Exception{

        ResultActions result = mockMvc
                .perform(delete("/products/{id}", notExistingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteShouldReturnBadRequestWhenIdIsDependence() throws Exception {

        ResultActions result = mockMvc
                .perform(delete("/products/{id}", dependentProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnForbiddenWhenClientLogged() throws Exception{

        ResultActions result = mockMvc
                .perform(delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldReturnUnauthorizedWhenUserIsNotLogged() throws Exception{

        ResultActions result = mockMvc
                .perform(delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());

    }


}
