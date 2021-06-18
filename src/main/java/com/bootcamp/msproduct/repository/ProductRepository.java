package com.bootcamp.msproduct.repository;

import com.bootcamp.msproduct.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, Integer> {
}
