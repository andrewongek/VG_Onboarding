package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.model.Product;
import com.onboardingassignment.oa.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Iterable<Product> findAllProductsPagedAndSorted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        return productRepository.findAll(pageable);
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

    public List<Product> getProductList() {
        return productRepository.findAll();
    }
}
