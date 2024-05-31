package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.IngredientDTO;
import dev.dandeac.data_api.entity.Ingredient;

public class IngredientBuilder {
    private IngredientBuilder() {
    }

    public static IngredientDTO toIngredientDTO(Ingredient ingredient) {
        return new IngredientDTO(ingredient.getIngredientId(), ingredient.getName(), ingredient.getMeasurementUnit(), ingredient.getPackaging());
    }


    public static Ingredient toIngredient(IngredientDTO ingredientDTO) {
        return new Ingredient(ingredientDTO.getName(), ingredientDTO.getMeasurementUnit(), ingredientDTO.getPackaging());
    }
}
