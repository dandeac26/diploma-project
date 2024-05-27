package dev.dandeac.data_api.repositories;

import dev.dandeac.data_api.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    boolean existsByName(String name);
    Ingredient findByName(String name);
}
