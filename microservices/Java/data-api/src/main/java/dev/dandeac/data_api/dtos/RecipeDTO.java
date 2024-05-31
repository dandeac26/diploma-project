package dev.dandeac.data_api.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecipeDTO {

    @NotNull(message = "Ingredient ID cannot be null")
    private UUID ingredientId;

    @NotNull(message = "Product ID cannot be null")
    private UUID productId;

    @NotNull(message = "Quantity cannot be null")
    private Double quantity;

    public RecipeDTO() {}

    public RecipeDTO(UUID productId, UUID ingredientId, Double quantity) {
        this.ingredientId = ingredientId;
        this.productId = productId;
        this.quantity = quantity;
    }

}