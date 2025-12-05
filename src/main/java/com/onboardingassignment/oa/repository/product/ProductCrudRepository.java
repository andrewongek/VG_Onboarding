package com.onboardingassignment.oa.repository.product;

import com.onboardingassignment.oa.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Integer> {
}
