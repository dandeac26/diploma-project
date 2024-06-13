package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "order_tb")
public class Order {

    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "client_id")
    private UUID clientId;

    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @Column(name = "delivery_needed")
    private Boolean deliveryNeeded;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "completion_time")
    private Time completionTime;

    @Column(name = "price")
    private Double price;

    @Column(name = "completed")
    private Boolean completed;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails;

    public Order(){}

    public Order(UUID orderId, UUID clientId, Boolean deliveryNeeded, Date completionDate, Time completionTime, Double price, Boolean completed) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.deliveryNeeded = deliveryNeeded;
        this.completionDate = completionDate;
        this.completionTime = completionTime;
        this.price = price;
        this.completed = completed;
    }

    public Order(UUID orderId, UUID clientId, Boolean deliveryNeeded, Date completionDate, Time completionTime, Boolean completed) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.deliveryNeeded = deliveryNeeded;
        this.completionDate = completionDate;
        this.completionTime = completionTime;
        this.completed = completed;
    }

    public Order(Boolean deliveryNeeded, Date completionDate, Time completionTime, Double price, Boolean completed) {
        this.deliveryNeeded = deliveryNeeded;
        this.completionDate = completionDate;
        this.completionTime = completionTime;
        this.price = price;
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(clientId, order.clientId) &&
                Objects.equals(client, order.client) && Objects.equals(deliveryNeeded, order.deliveryNeeded) &&
                Objects.equals(completionDate, order.completionDate) && Objects.equals(completionTime, order.completionTime) &&
                Objects.equals(price, order.price) && Objects.equals(completed, order.completed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, client, deliveryNeeded, completionDate, completionTime, price, completed);
    }
}
