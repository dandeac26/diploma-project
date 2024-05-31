package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.RecipeDTO;
import dev.dandeac.data_api.dtos.builders.RecipeBuilder;
import dev.dandeac.data_api.entity.Recipe;
import dev.dandeac.data_api.entity.RecipeId;
import dev.dandeac.data_api.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeDTO> findRecipes() {
        List<Recipe> recipeList = recipeRepository.findAll();
        return recipeList.stream()
                .map(RecipeBuilder::toRecipeDTO)
                .collect(Collectors.toList());
    }

    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {

        if (recipeRepository.existsByIdIngredientIdAndIdProductId(recipeDTO.getIngredientId(), recipeDTO.getProductId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe for product " + recipeDTO.getProductId() + " already exists");
        }
        Recipe recipe = RecipeBuilder.toRecipe(recipeDTO);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeBuilder.toRecipeDTO(savedRecipe);
    }

    public void deleteRecipe(String productId, String ingredientId) {
        RecipeId id = new RecipeId(UUID.fromString(productId), UUID.fromString(ingredientId));
        if (!recipeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with product id " + productId + " and ingredient id "+ ingredientId + " does not exist");
        }
        recipeRepository.deleteById(id);
    }

    public void deleteProductRecipe(String productId) {
        List<Recipe> recipes = recipeRepository.findByIdProductId(UUID.fromString(productId));
        if (recipes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipes with product id " + productId + " do not exist");
        }
        recipeRepository.deleteAll(recipes);
    }

    public RecipeDTO updateRecipe(RecipeId recipeId, RecipeDTO recipeDTO) {
        if (!recipeRepository.existsById(recipeId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + recipeId + " does not exist");
        }

        Recipe recipe = RecipeBuilder.toRecipe(recipeDTO);
        recipe.setId(recipeId);
        Recipe updatedRecipe = recipeRepository.save(recipe);
        return RecipeBuilder.toRecipeDTO(updatedRecipe);
    }

    public List<RecipeDTO> findRecipeByProductId(String productId) {
        List<Recipe> recipes = recipeRepository.findByIdProductId(UUID.fromString(productId));
        return recipes.stream()
                .map(RecipeBuilder::toRecipeDTO)
                .collect(Collectors.toList());
    }


    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    public RecipeDTO findRecipeById(RecipeId recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + recipeId + " does not exist"));
        return RecipeBuilder.toRecipeDTO(recipe);
    }
}
