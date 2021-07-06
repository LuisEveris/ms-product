package com.bootcamp.msproduct.service;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.repository.ProductRepository;
import com.bootcamp.msproduct.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @MockBean
    ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void getAllProducts() {
        ProductDTO productDTO1 = new ProductDTO(1, "name1", "type1");
        ProductDTO productDTO2 = new ProductDTO(2, "name2", "type2");

        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(productDTO1,productDTO2).map(AppUtils::dtoToEntity));

        Flux<ProductDTO> allProducts = service.getAllProducts();

        StepVerifier.create(allProducts)
                .expectNext(productDTO1,productDTO2)
                .verifyComplete()
        ;
    }

    @Test
    void getProduct() {
        ProductDTO productDTO = new ProductDTO(1, "name", "type");

        Mockito.when(repository.findById(productDTO.getId()))
                .thenReturn(Mono.just(productDTO).map(AppUtils::dtoToEntity));

        Mono<ProductDTO> productDTOMono = service.getProduct(productDTO.getId());


        StepVerifier.create(productDTOMono)
                .expectNext(productDTO).verifyComplete();
    }
}