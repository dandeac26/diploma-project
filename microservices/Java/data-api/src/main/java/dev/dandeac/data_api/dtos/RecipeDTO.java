package dev.dandeac.data_api.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDTO {

    @NotNull(message = "Ingredient ID cannot be null")
    private UUID ingredientId;

    @NotNull(message = "Product ID cannot be null")
    private UUID productId;

    @NotNull(message = "Quantity cannot be null")
    private Double quantity;

    private String ingredientMeasurementUnit;
    private String ingredientName;


    public RecipeDTO() {}

    public RecipeDTO(UUID productId, UUID ingredientId, Double quantity) {
        this.ingredientId = ingredientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public RecipeDTO(UUID productId, UUID ingredientId, Double quantity, String ingredientMeasurementUnit, String ingredientName) {
        this.ingredientId = ingredientId;
        this.productId = productId;
        this.quantity = quantity;
        this.ingredientMeasurementUnit = ingredientMeasurementUnit;
        this.ingredientName = ingredientName;
    }

}