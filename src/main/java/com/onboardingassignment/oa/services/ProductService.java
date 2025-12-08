package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.dto.ProductDto;
import com.onboardingassignment.oa.model.Product;
import com.onboardingassignment.oa.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(value="PRODUCT_CACHE", key = "#id")
    public ProductDto getProductById(int id) {
        return toDto(productRepository.getById(id));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void buy(String code) {
        Product product = productRepository.findByCode(code);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + code);
        }

        if (product.getStock() <= 0) {
            throw new IllegalStateException("Product out of stock: " + code);
        }

        product.setStock(product.getStock() - 1);
        productRepository.save(product);
    }

    public List<ProductDto> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<ProductDto> getProductListFromIds(List<Integer> ids) {
        return productRepository.findAllById(ids)
                .stream()
                .map(this::toDto)
                .toList();

    }

    private ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getLanguage(),
                product.getStock(),
                product.getPrice()
        );
    }
}
