package com.onboardingassignment.oa.services;

import com.onboardingassignment.oa.entities.Product;
import com.onboardingassignment.oa.repository.product.ProductCrudRepository;
import com.onboardingassignment.oa.repository.product.ProductJpaRepository;
import com.onboardingassignment.oa.repository.product.ProductPagingAndSortingRepository;
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
}
