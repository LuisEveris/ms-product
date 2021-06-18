package com.bootcamp.msproduct.service;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.repository.ProductRepository;
import com.bootcamp.msproduct.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Flux<ProductDTO> getAllProducts() {
        return repository.findAll().map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> getProduct(Integer id) {
        return repository.findById(id).map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, Integer id) {
        return repository.findById(id)
                .flatMap(p -> productDTOMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(repository::save)
                .map(AppUtils::entityToDTO);
    }

    public Mono<Void> deleteProduct(Integer id) {
        return repository.deleteById(id);
    }
}
