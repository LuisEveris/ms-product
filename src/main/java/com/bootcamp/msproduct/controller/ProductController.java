package com.bootcamp.msproduct.controller;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping
    public Flux<ResponseEntity<ProductDTO>> allProducts() {
        return service.getAllProducts()
                .map(ResponseEntity::ok);

        /* This code commented works!
        return service.getAllProducts()
                .filter(p -> !(p.equals(0)))
                .map(ResponseEntity::ok);*/
        /*return service.getAllProducts()
                .map{a->ResponseEntity.ok(it)};*/
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> getProduct(@PathVariable Integer id) {
        return service.getProduct(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("update/{id}")
    public Mono<ResponseEntity<ProductDTO>> saveProduct(@PathVariable Integer id,
                                                        @RequestBody Mono<ProductDTO> productDTOMono) {
        return service.updateProduct(productDTOMono, id)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Integer id) {
        return service.deleteProduct(id)
                .map(ResponseEntity::ok);
    }
}
