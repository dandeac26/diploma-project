package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "order_tb")
public class Order {

    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "order_id")
    private UUID orderId;

    @Setter
    @Column(name = "client_id")
    private UUID clientId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @Setter
    @Column(name = "location")
    private String location;

    @Setter
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Setter
    @Column(name = "delivery_time")
    private Time deliveryTime;

    @Setter
    @Column(name = "price")
    private Double price;

    public Order(){}

    public Order(String location, Date deliveryDate, Time deliveryTime, Double price) {
        this.location = location;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(clientId, order.clientId) && Objects.equals(client, order.client) && Objects.equals(location, order.location) && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(deliveryTime, order.deliveryTime) && Objects.equals(price, order.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, client, location, deliveryDate, deliveryTime, price);
    }
}
