package dev.dandeac.data_api.dtos;

import dev.dandeac.data_api.entity.StockId;
import jakarta.validation.constraints.AssertTrue;
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

    @NotNull(message = "Max Quantity cannot be null")
    @Positive(message = "Max Quantity must be positive")

    private Integer maxQuantity;


    private String ingredientName;

    private String providerName;

    private String packaging;


    public StockDTO() {}

    public StockDTO(UUID ingredientId, UUID providerId, Integer quantity, Double price, Integer maxQuantity, String ingredientName, String providerName, String packaging) {
        this.ingredientId = ingredientId;
        this.providerId = providerId;
        this.quantity = quantity;
        this.price = price;
        this.maxQuantity = maxQuantity;
        this.ingredientName = ingredientName;
        this.providerName = providerName;
        this.packaging = packaging;
    }

    public StockDTO(UUID ingredientId, UUID providerId, Integer quantity, Double price, Integer maxQuantity, String ingredientName, String providerName) {
        this.ingredientId = ingredientId;
        this.providerId = providerId;
        this.quantity = quantity;
        this.price = price;
        this.maxQuantity = maxQuantity;
        this.ingredientName = ingredientName;
        this.providerName = providerName;
    }

    public StockDTO(Integer quantity, Double price, Integer maxQuantity ) {
        this.quantity = quantity;
        this.price = price;
        this.maxQuantity = maxQuantity;
    }

    @AssertTrue(message = "Quantity must be smaller or equal to Max Quantity")
    public boolean isQuantityValid() {
        return quantity <= maxQuantity;
    }
}
