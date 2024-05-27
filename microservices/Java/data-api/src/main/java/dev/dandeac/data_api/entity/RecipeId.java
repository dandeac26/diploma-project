package dev.dandeac.data_api.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class RecipeId implements Serializable {

    private UUID ingredientId;
    private UUID productId;

    public RecipeId() {}

    public RecipeId(UUID ingredientId, UUID productId) {
        this.ingredientId = ingredientId;
        this.productId = productId;
    }

}
