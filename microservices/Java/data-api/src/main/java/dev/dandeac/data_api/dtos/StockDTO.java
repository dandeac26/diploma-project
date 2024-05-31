package dev.dandeac.data_api.dtos;

import dev.dandeac.data_api.entity.StockId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockDTO {
    @NotNull(message = "Ingredient ID cannot be null")
    private UUID ingredientId;

    @NotNull(message = "Provider ID cannot be null")
    private UUID providerId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    public StockDTO() {}

    public StockDTO(UUID ingredientId, UUID providerId, Integer quantity, Double price) {
        this.ingredientId = ingredientId;
        this.providerId = providerId;
        this.quantity = quantity;
        this.price = price;
    }

    public StockDTO(Integer quantity, Double price) {
        this.quantity = quantity;
        this.price = price;
    }
}
