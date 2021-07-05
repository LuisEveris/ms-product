package com.bootcamp.msproduct.web;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService service;

    @CircuitBreaker(name = "allProductFallBack", fallbackMethod = "allProductFallBackMethod")
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> allProducts() {
        log.info("getting all products");
        return service.getAllProducts().delaySequence(Duration.ofMillis(500));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ResponseEntity<ProductDTO>> getProduct(@PathVariable Integer id) {
        log.info("getting a product by Id {}", id);
        return service.getProduct(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The product with ID " + id + " not found.")));
    }

    @PostMapping
    public Mono<ResponseEntity<ProductDTO>> saveProduct(@RequestBody Mono<ProductDTO> productDTOMono) {
        log.info("saving a new product {}", productDTOMono);
        return service.saveProduct(productDTOMono)
                .map(ResponseEntity::ok);
    }

    @PutMapping("update/{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@PathVariable Integer id,
                                                          @RequestBody Mono<ProductDTO> productDTOMono) {
        log.info("updating an existing product | id : {} productDTO : {}", id, productDTOMono);
        return service.updateProduct(id, productDTOMono)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Integer id) {
        log.info("deleting a new product {}", id);
        return service.deleteProduct(id)
                .map(ResponseEntity::ok);
    }
    //fallbacks

    public Mono<ResponseEntity<String>> allProductFallBackMethod() {
        return Mono.just("Cannot get all products now, try later, please.")
                .map(ResponseEntity::ok);
    }

}