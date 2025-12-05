package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.dto.ProductList;
import com.onboardingassignment.oa.model.Product;
import com.onboardingassignment.oa.repository.product.ProductCrudRepository;
import com.onboardingassignment.oa.repository.product.ProductJpaRepository;
import com.onboardingassignment.oa.repository.product.ProductPagingAndSortingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductCrudRepository productCrudRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductPagingAndSortingRepository productPagingAndSortingRepository;

    public Product saveProduct(Product product) {
        return productCrudRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productJpaRepository.findAll();
    }

    public Iterable<Product> findAllProductsPagedAndSorted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        return productPagingAndSortingRepository.findAll(pageable);
    }

    @Transactional
    public void buy(String code) {
        Product product = productJpaRepository.findByCode(code);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + code);
        }

        if (product.getStock() <= 0) {
            throw new IllegalStateException("Product out of stock: " + code);
        }

        product.setStock(product.getStock() - 1);
        productCrudRepository.save(product);
    }

    public ProductList getProductList() {
        List<Product> products = productJpaRepository.findAll();
        System.out.println(products);
        return new ProductList(products);
    }
}
