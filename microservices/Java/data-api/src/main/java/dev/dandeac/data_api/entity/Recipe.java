package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "recipes_tb")
public class Recipe {

    @EmbeddedId
    private RecipeId id;


    @Column(name = "quantity")
    private Double quantity;

    public Recipe(){}

    public Recipe(RecipeId id, Double quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
