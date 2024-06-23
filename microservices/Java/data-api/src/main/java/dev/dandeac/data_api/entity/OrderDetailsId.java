package dev.dandeac.data_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class OrderDetailsId implements Serializable {
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private UUID productId;

    public OrderDetailsId() {}

    public OrderDetailsId(UUID orderId, UUID productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailsId orderDetailsId)) return false;
        return getOrderId().equals(orderDetailsId.getOrderId()) && getProductId().equals(orderDetailsId.getProductId());
    }

    @Override
    public int hashCode() {
        return getOrderId().hashCode() + getProductId().hashCode();
    }
}
