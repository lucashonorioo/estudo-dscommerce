package com.estudo.dscommerce.dto.response;

import com.estudo.dscommerce.model.Order;
import com.estudo.dscommerce.model.OrderItem;
import com.estudo.dscommerce.model.OrderStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;

    private ClientResponseDTO client;
    private PaymentResponseDTO payment;

    List<ItemsResponseDTO> items = new ArrayList<>();

    public OrderResponseDTO(){

    }

    public OrderResponseDTO(Long id, Instant moment, OrderStatus status, ClientResponseDTO client, PaymentResponseDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderResponseDTO(Order order) {
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
