package dev.dandeac.data_api.dtos.builders;

import dev.dandeac.data_api.dtos.ProductDTO;
import dev.dandeac.data_api.entity.Product;

public class ProductBuilder {
    private ProductBuilder() {
    }

    public static ProductDTO toProductDTO(Product product) {
        return new ProductDTO(product.getProductId(), product.getName(), product.getPrice());
    }


    public static Product toProduct(ProductDTO productDTO) {
        return new Product(productDTO.getName(), productDTO.getPrice());
    }
}
