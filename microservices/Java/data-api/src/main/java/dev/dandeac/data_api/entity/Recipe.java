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

    @Id
    @Column(name = "ingredient_id")
    private UUID ingredientId;

    @Id
    @Column(name = "product_id")
    private UUID productId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private Ingredient ingredient;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Setter
    @Column(name = "quantity")
    private Double quantity;

    public Recipe(){}

    public Recipe(UUID ingredientId, UUID productId, Double quantity) {
        this.ingredientId = ingredientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Recipe(UUID ingredientId, UUID productId, Ingredient ingredient, Product product, Double quantity) {
        this.ingredientId = ingredientId;
        this.productId = productId;
        this.ingredient = ingredient;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(ingredientId, recipe.ingredientId) && Objects.equals(productId, recipe.productId) && Objects.equals(ingredient, recipe.ingredient) && Objects.equals(product, recipe.product) && Objects.equals(quantity, recipe.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, productId, ingredient, product, quantity);
    }
}
