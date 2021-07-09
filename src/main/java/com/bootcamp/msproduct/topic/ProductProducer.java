package com.bootcamp.msproduct.topic;

import com.bootcamp.msproduct.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public static final String PRODUCT_TOPIC = "product-topic";

    public void sendProductToTopic(ProductDTO data) {
        kafkaTemplate.send(PRODUCT_TOPIC, data);
    }

}
