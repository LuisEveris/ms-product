package com.bootcamp.msproduct.controller;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping
    public Flux<ProductDTO> allProducts() {
        log.info("getting all products");
        return service.getAllProducts()
                .delaySequence(Duration.ofSeconds(2));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> getProduct(@PathVariable Integer id) {
        log.info("getting one product by Id");
        return service.getProduct(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> saveProduct(@RequestBody Mono<ProductDTO> productDTOMono) {
        log.info("saving a new product");
        return service.saveProduct(productDTOMono)
                .map(ResponseEntity::ok);
    }

    @PutMapping("update/{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable Integer id,
                                                          @RequestBody Mono<ProductDTO> productDTOMono) {
        log.info("updating an existing product");
        return service.updateProduct(productDTOMono, id)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Integer id) {
        log.info("deleting a new product", this);
        return service.deleteProduct(id)
                .map(ResponseEntity::ok);
    }
}
