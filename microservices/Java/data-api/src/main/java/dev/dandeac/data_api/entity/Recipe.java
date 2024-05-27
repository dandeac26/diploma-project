package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "recipes_tb")
public class Recipe {

    @EmbeddedId
    private RecipeId id;

    @Setter
    @Column(name = "quantity")
    private Double quantity;

    public Recipe(){}

    public Recipe(UUID ingredientId, UUID productId, Double quantity) {
        this.id = new RecipeId(ingredientId, productId);
        this.quantity = quantity;
    }
}
