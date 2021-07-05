package com.bootcamp.msproduct.service;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.repository.ProductRepository;
import com.bootcamp.msproduct.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Flux<ProductDTO> getAllProducts() {
        log.debug("getAll clients | method from ProductService");
        return repository.findAll().map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> getProduct(Integer id) {
        log.debug("get 1 product by id | method from ProductService {}", id);

        return repository.findById(id)
                .map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        log.debug("save a product| method from ProductService : {}", productDTOMono);
        return productDTOMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)
                .map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> updateProduct(Integer id, Mono<ProductDTO> productDTOMono) {
        log.debug("update a product by ID| method from ProductService the id is : {} and the ProductDTO is : {}", id, productDTOMono);

        return repository.findById(id)
                .flatMap(p -> productDTOMono.map(AppUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(repository::save)
                .map(AppUtils::entityToDTO);
    }

    public Mono<Void> deleteProduct(Integer id) {
        log.debug("delete a product| method from ProductService the id is : {}", id);
        return repository.deleteById(id);
    }
}
