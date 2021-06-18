package com.bootcamp.msproduct.utils;

import com.bootcamp.msproduct.dto.ProductDTO;
import com.bootcamp.msproduct.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDTO entityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public static Product dtoToEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }


}
