package dev.dandeac.data_api.dtos;

import dev.dandeac.data_api.entity.Client;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class OrderDetailsDTO {


    private UUID orderId;

    private UUID clientId;

    private Client client;

    private String location;

    private Date deliveryDate;

    private Time deliveryTime;

    private Double price;

    public OrderDetailsDTO(){}

    public OrderDetailsDTO(UUID orderId, UUID clientId, Client client, String location, Date deliveryDate, Time deliveryTime, Double price) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.client = client;
        this.location = location;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.price = price;
    }

    public OrderDetailsDTO(UUID orderId, UUID clientId, String location, Date deliveryDate, Time deliveryTime, Double price) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.location = location;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.price = price;
    }
    public OrderDetailsDTO(UUID orderId, UUID clientId, Double price) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.price = price;
    }

    public OrderDetailsDTO(UUID orderId, UUID clientId) {
        this.orderId = orderId;
        this.clientId = clientId;
    }

}
