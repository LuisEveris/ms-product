package com.bootcamp.msproduct.web;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.entity.Product;
import com.bootcamp.msproduct.repository.ProductRepository;
import com.bootcamp.msproduct.service.ProductService;
import com.bootcamp.msproduct.utils.AppUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProductController.class)
@Import(ProductService.class)
class ProductControllerTest {

    @MockBean
    ProductRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testCreateProduct() {
        Product product = AppUtils.dtoToEntity(new ProductDTO(1, "Luis", "pasivo"));

        Mockito.when(repository.save(product)).thenReturn(Mono.just(product));

        webClient.post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(product))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(repository, times(1)).save(product);
    }

}