package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.RecipeDTO;
import dev.dandeac.data_api.dtos.builders.RecipeBuilder;
import dev.dandeac.data_api.entity.Recipe;
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

        if (recipeRepository.existsByName(recipeDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe with name " + recipeDTO.getName() + " already exists");
        }
        Recipe recipe = RecipeBuilder.toRecipe(recipeDTO);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return RecipeBuilder.toRecipeDTO(savedRecipe);
    }

    public void deleteRecipe(String recipeId) {
        if (!recipeRepository.existsById(UUID.fromString(recipeId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + recipeId + " does not exist");
        }
        recipeRepository.deleteById(UUID.fromString(recipeId));
    }

    public RecipeDTO updateRecipe(String recipeId, RecipeDTO recipeDTO) {
        if (!recipeRepository.existsById(UUID.fromString(recipeId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + recipeId + " does not exist");
        }

        if (recipeRepository.existsByName(recipeDTO.getName()) && !recipeRepository.findByName(recipeDTO.getName()).getRecipeId().equals(UUID.fromString(recipeId) )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe with name " + recipeDTO.getName() + " already exists");
        }
        Recipe recipe = RecipeBuilder.toRecipe(recipeDTO);
        recipe.setRecipeId(UUID.fromString(recipeId));
        Recipe updatedRecipe = recipeRepository.save(recipe);
        return RecipeBuilder.toRecipeDTO(updatedRecipe);
    }

    public RecipeDTO findRecipeById(String recipeId) {
        Recipe recipe = recipeRepository.findById(UUID.fromString(recipeId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id " + recipeId + " does not exist"));
        return RecipeBuilder.toRecipeDTO(recipe);
    }

    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }
}
