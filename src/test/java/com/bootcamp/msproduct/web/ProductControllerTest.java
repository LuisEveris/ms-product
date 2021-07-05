package com.bootcamp.msproduct.web;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.repository.ProductRepository;
import com.bootcamp.msproduct.service.ProductService;
import com.bootcamp.msproduct.utils.AppUtils;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    void testGetProducts() {
        ProductDTO productDTO = new ProductDTO(1, "cuenta de ahorro", "pasivo");
        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(productDTO).map(AppUtils::dtoToEntity));

        webClient.get()
                .uri("/products")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO(1, "Luis", "pasivo");
        Mono<ProductDTO> productDTOMono = Mono.just(productDTO);

        Mockito.when(repository.insert(AppUtils.dtoToEntity(productDTO)))
                .thenReturn(Mono.just(AppUtils.dtoToEntity(productDTO)));

        webClient.post()
                .uri("/products")
                .body(productDTOMono, ProductDTO.class)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(repository, times(1)).insert(AppUtils.dtoToEntity(productDTO));
    }

}