package com.estudo.dscommerce.dto.request;

import com.estudo.dscommerce.dto.response.ClientResponseDTO;
import com.estudo.dscommerce.dto.response.ItemsResponseDTO;
import com.estudo.dscommerce.dto.response.PaymentResponseDTO;
import com.estudo.dscommerce.model.Order;
import com.estudo.dscommerce.model.OrderItem;
import com.estudo.dscommerce.model.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderRequestDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;

    private ClientResponseDTO client;
    private PaymentResponseDTO payment;

    @NotEmpty(message = "Deve ter pelo menos um item")
    List<ItemsResponseDTO> items = new ArrayList<>();

    public OrderRequestDTO(){

    }

    public OrderRequestDTO(Long id, Instant moment, OrderStatus status, ClientResponseDTO client, PaymentResponseDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderRequestDTO(Order order) {
        id = order.getId();
        moment = order.getMoment();
        this.status = order.getStatus();
        client = new ClientResponseDTO(order.getClient());
        this.payment = (order.getPayment() == null) ? null : new PaymentResponseDTO(order.getPayment());
        for(OrderItem orderItem : order.getItems()){
            items.add(new ItemsResponseDTO(orderItem));
        }
    }


    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientResponseDTO getClient() {
        return client;
    }

    public PaymentResponseDTO getPayment() {
        return payment;
    }

    public List<ItemsResponseDTO> getItems() {
        return items;
    }

    public Double getTotal(){
        double sum  = 0;
        for(ItemsResponseDTO itemsResponseDTO : items){
            sum += itemsResponseDTO.getSubTotal();
        }
          return sum;
    }
}
