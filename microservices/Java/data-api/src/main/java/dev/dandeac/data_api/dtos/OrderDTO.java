package dev.dandeac.data_api.dtos;


import dev.dandeac.data_api.entity.Client;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class OrderDTO {


    private UUID orderId;

    private UUID clientId;

    private Client client;

    private String location;

    private Date deliveryDate;

    private Time deliveryTime;

    private Double price;

    public OrderDTO(){}

    public OrderDTO(UUID orderId, UUID clientId, Client client, String location, Date deliveryDate, Time deliveryTime, Double price) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.client = client;
        this.location = location;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.price = price;
    }

    public OrderDTO(UUID orderId, UUID clientId, String location, Date deliveryDate, Time deliveryTime, Double price) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.location = location;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.price = price;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(location, orderDTO.location) &&
                Objects.equals(deliveryDate, orderDTO.deliveryDate) &&
                Objects.equals(deliveryTime, orderDTO.deliveryTime) &&
                Objects.equals(price, orderDTO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, deliveryDate, deliveryTime, price);
    }
}
