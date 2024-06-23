package dev.dandeac.data_api.services;

import dev.dandeac.data_api.dtos.ProductDTO;
import dev.dandeac.data_api.dtos.builders.ProductBuilder;
import dev.dandeac.data_api.entity.Product;
import dev.dandeac.data_api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(ProductBuilder::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {

        if (productRepository.existsByName(productDTO.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with name " + productDTO.getName() + " already exists");
        }
        Product product = ProductBuilder.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);
        return ProductBuilder.toProductDTO(savedProduct);
    }

    public void deleteProduct(String productId) {
        try{
            if (!productRepository.existsById(UUID.fromString(productId))) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + productId + " does not exist");
            }
            productRepository.deleteById(UUID.fromString(productId));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete product. It is used in a recipe."
            );
        }
    }

    public ProductDTO updateProduct(String productId, ProductDTO productDTO) {
        if (!productRepository.existsById(UUID.fromString(productId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + productId + " does not exist");
        }

        if (productRepository.existsByName(productDTO.getName()) && !productRepository.findByName(productDTO.getName()).getProductId().equals(UUID.fromString(productId) )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with name " + productDTO.getName() + " already exists");
        }
        Product product = ProductBuilder.toProduct(productDTO);
        product.setProductId(UUID.fromString(productId));
        Product updatedProduct = productRepository.save(product);
        return ProductBuilder.toProductDTO(updatedProduct);
    }

    public ProductDTO findProductById(String productId) {
        Product product = productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + productId + " does not exist"));
        return ProductBuilder.toProductDTO(product);
    }

    public void deleteAllProducts() {
        try{
            productRepository.deleteAll();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete products. They are used in a recipe."
            );
        }
    }

    public boolean existsById(UUID productId) {
        return productRepository.existsById(productId);
    }

    public Product findById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + productId + " does not exist"));
    }
}
