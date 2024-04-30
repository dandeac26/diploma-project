package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "stocks_tb")
public class Stock {

    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "stocks_id")
    private UUID stockId;

    @Setter
    @Column(name = "ingredient_id")
    private UUID ingredientId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private Ingredient ingredient;

    @Setter
    @Column(name = "quantity")
    private Integer quantity;

    @Setter
    @Column(name = "price")
    private Double price;

    public Stock(){}

    public Stock(UUID stockId, UUID ingredientId, Ingredient ingredient, Integer quantity, Double price) {
        this.stockId = stockId;
        this.ingredientId = ingredientId;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(stockId, stock.stockId) && Objects.equals(ingredientId, stock.ingredientId) && Objects.equals(ingredient, stock.ingredient) && Objects.equals(quantity, stock.quantity) && Objects.equals(price, stock.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockId, ingredientId, ingredient, quantity, price);
    }
}
