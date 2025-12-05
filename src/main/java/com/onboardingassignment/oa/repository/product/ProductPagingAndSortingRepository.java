package com.onboardingassignment.oa.repository.product;

import com.onboardingassignment.oa.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductPagingAndSortingRepository extends PagingAndSortingRepository<Product, Integer> {
}
