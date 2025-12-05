package com.onboardingassignment.oa.repository.product;

import com.onboardingassignment.oa.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Integer> {
}
