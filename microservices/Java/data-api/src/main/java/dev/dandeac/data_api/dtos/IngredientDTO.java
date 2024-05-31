package dev.dandeac.data_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IngredientDTO {
    private UUID ingredientId;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Measurement unit cannot be null")
    @NotEmpty(message = "Measurement unit cannot be empty")
    private String measurementUnit;

    @NotNull(message = "Packaging cannot be null")
    @NotEmpty(message = "Packaging cannot be empty")
    private String packaging;

    public IngredientDTO() {
    }

    public IngredientDTO(UUID ingredientId, String name, String measurementUnit, String packaging) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.measurementUnit = measurementUnit;
        this.packaging = packaging;
    }
}
