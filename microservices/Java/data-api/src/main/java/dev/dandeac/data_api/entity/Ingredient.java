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

@Setter
@Getter
@Entity
@Table(name = "ingredients_tb")
public class Ingredient {
    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "ingredient_id")
    private UUID ingredientId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Column(name = "packaging")
    private String packaging;

    public Ingredient(){}

    public Ingredient(UUID ingredientId, String name, String measurementUnit, String packaging) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.measurementUnit = measurementUnit;
        this.packaging = packaging;
    }

    public Ingredient(String name, String measurementUnit, String packaging) {
        this.name = name;
        this.measurementUnit = measurementUnit;
        this.packaging = packaging;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(ingredientId, that.ingredientId) && Objects.equals(measurementUnit, that.measurementUnit) && Objects.equals(packaging, that.packaging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, measurementUnit, packaging);
    }
}
