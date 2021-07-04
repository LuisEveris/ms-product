package com.bootcamp.msproduct.product;

import com.bootcamp.msproduct.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    void persist() {
        Flux<ProductDTO> saved = Flux
                .just(new ProductDTO(1, "Luis", "pasivo"), new ProductDTO(2, "Enrique", "empresarial"))
                .flatMap(this.reactiveMongoTemplate::save);

        Flux<ProductDTO> interaction = this.reactiveMongoTemplate
                .dropCollection(ProductDTO.class)
                .thenMany(saved)
                .thenMany(this.reactiveMongoTemplate.findAll(ProductDTO.class));

        Predicate<ProductDTO> predicate = productDTO ->
                StringUtils.hasText(productDTO.getId().toString()) && (
                        productDTO.getName().equalsIgnoreCase("Luis") ||
                                productDTO.getName().equalsIgnoreCase("Enrique"));

        StepVerifier
                .create(interaction)
                .expectNextMatches(predicate)
                .expectNextMatches(predicate)
                .verifyComplete();
    }


}