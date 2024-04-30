package dev.dandeac.data_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "ingredients_tb")
public class Ingredient {
    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "ingredient_id")
    private UUID ingredientId;

    @Setter
    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Setter
    @Column(name = "price")
    private Double price;

    public Ingredient(){}

    public Ingredient(UUID ingredientId, String measurementUnit, Double price) {
        this.ingredientId = ingredientId;
        this.measurementUnit = measurementUnit;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(ingredientId, that.ingredientId) && Objects.equals(measurementUnit, that.measurementUnit) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, measurementUnit, price);
    }
}
