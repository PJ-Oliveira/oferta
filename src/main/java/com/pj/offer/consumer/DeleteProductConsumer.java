package com.pj.offer.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.offer.config.rabbitmq.RabbitMQConfig;
import com.pj.offer.domain.Product;
import com.pj.offer.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
public class DeleteProductConsumer {

    @Autowired
    private ProductService productService;

    @JsonIgnoreProperties
    @RabbitListener(queues = RabbitMQConfig.QUEUE, containerFactory = "offerContainerFactory")
    public void listener(String message) {
        try {
            Product product = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false).
                    readValue(message, Product.class);
            productService.deleteProductByIdProduct(product.getIdProduct());
        }catch (Exception exception) { exception.printStackTrace();}
    }
}