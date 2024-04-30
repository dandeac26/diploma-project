package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "order_details_tb")
public class OrderDetails {
    @Id
    @Setter
    @Column(name = "order_id")
    private UUID orderId;

    @Id
    @Setter
    @Column(name = "product_id")
    private UUID productId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Setter
    @Column(name = "quantity")
    private Integer quantity;

    public OrderDetails(){}
    public OrderDetails(UUID orderId, UUID productId, Order order, Product product, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) && Objects.equals(order, that.order) && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, order, product, quantity);
    }
}
