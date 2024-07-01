package dev.dandeac.data_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "stocks_tb")
public class Stock {

    @EmbeddedId
    private StockId id;

    @MapsId("ingredientId")
    @ManyToOne
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private Ingredient ingredient;


    @MapsId("providerId")
    @ManyToOne
    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    private Provider provider;

    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "price")
    private Double price;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    @Column(name ="quantity_per_package")
    private Integer quantityPerPackage;

    public Stock(){}

    public Stock(StockId id, Integer quantity, Double price, Integer maxQuantity, Integer quantityPerPackage) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.maxQuantity = maxQuantity;
        this.quantityPerPackage = quantityPerPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(id, stock.id) && Objects.equals(quantity, stock.quantity) && Objects.equals(price, stock.price) && Objects.equals(maxQuantity, stock.maxQuantity)&& Objects.equals(quantityPerPackage, stock.quantityPerPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, maxQuantity, quantityPerPackage);
    }
}
