package com.estudo.dscommerce.dto.response;

import com.estudo.dscommerce.model.Payment;

import java.time.Instant;

public class PaymentResponseDTO {

    private Long id;
    private Instant moment;

    public PaymentResponseDTO(){

    }

    public PaymentResponseDTO(Long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }

    public PaymentResponseDTO(Payment payment) {
        id = payment.getId();
        moment = payment.getMoment();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }
}
