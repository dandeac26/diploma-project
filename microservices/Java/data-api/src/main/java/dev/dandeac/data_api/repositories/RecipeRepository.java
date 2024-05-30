package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Recipe;
import dev.dandeac.data_api.entity.RecipeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, RecipeId> {

}