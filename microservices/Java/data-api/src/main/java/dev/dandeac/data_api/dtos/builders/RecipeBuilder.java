package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.RecipeDTO;
import dev.dandeac.data_api.entity.Ingredient;
import dev.dandeac.data_api.entity.Recipe;
import dev.dandeac.data_api.entity.RecipeId;
import dev.dandeac.data_api.services.IngredientService;
import dev.dandeac.data_api.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeBuilder {

    private final IngredientService ingredientService;

    @Autowired
    public RecipeBuilder(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public RecipeDTO toRecipeDTO(Recipe recipe) {
        Ingredient ingredient = ingredientService.findById(recipe.getId().getIngredientId());

        return new RecipeDTO(
                recipe.getId().getProductId(),
                recipe.getId().getIngredientId(),
                recipe.getQuantity(),
                ingredient.getMeasurementUnit(),
                ingredient.getName()
        );
    }

    public static Recipe toRecipe(RecipeDTO recipeDTO) {
        RecipeId id = new RecipeId(recipeDTO.getProductId(), recipeDTO.getIngredientId());
        return new Recipe(id, recipeDTO.getQuantity());
    }
}
