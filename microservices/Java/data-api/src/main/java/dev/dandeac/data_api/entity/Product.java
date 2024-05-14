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
@Table(name = "products_tb")
public class Product {

    @Id
    @UuidGenerator(style=UuidGenerator.Style.RANDOM)
    @Column(name = "product_id")
    private UUID productId;


    @Column(name = "name", nullable = false, columnDefinition = "text CHECK (length(name) > 0)", unique = true)
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "double precision CHECK (price > 0)")
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    public Product(){}

    public Product( String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product( String name, Double price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price);
    }
}
