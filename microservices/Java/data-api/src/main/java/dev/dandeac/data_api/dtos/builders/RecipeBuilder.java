package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.RecipeDTO;
import dev.dandeac.data_api.entity.Recipe;
import dev.dandeac.data_api.entity.RecipeId;

public class RecipeBuilder {
    private RecipeBuilder() {
    }

    public static RecipeDTO toRecipeDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getId().getProductId(),
                recipe.getId().getIngredientId(),
                recipe.getQuantity()
        );
    }

    public static Recipe toRecipe(RecipeDTO recipeDTO) {
        RecipeId id = new RecipeId(recipeDTO.getProductId(), recipeDTO.getIngredientId());
        return new Recipe(id, recipeDTO.getQuantity());
    }
}
