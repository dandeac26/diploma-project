package dev.dandeac.data_api.dtos;

import dev.dandeac.data_api.entity.Client;
import dev.dandeac.data_api.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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


    @NotNull(message = "Order ID cannot be null")
    private UUID orderId;

    @NotNull(message = "Product ID cannot be null")
    private UUID productId;

    @NotNull(message = "Quantity ID cannot be null")
    private Integer quantity;

    private Product product;

    public OrderDetailsDTO(){}

    public OrderDetailsDTO(UUID orderId, UUID productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsDTO that = (OrderDetailsDTO) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, quantity);
    }

}
