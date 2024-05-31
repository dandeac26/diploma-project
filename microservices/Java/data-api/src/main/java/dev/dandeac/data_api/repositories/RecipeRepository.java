package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Recipe;
import dev.dandeac.data_api.entity.RecipeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, RecipeId> {
    boolean existsByIdIngredientIdAndIdProductId(UUID ingredientId, UUID productId);
    Recipe findByIdIngredientIdAndIdProductId(UUID ingredientId, UUID productId);
    List<Recipe> findByIdProductId(UUID productId);
}