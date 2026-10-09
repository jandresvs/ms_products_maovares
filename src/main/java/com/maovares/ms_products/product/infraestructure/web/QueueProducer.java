package com.maovares.ms_products.product.infraestructure.web;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueProducer {

    @Value("${queue.connection-string:}")
    private String connectionString;

    // Encola un evento OrderCreated en 'ordersqueue' (mismo formato que el productor Node: JSON -> base64)
    public void sendOrderCreated(String orderJson) {
        QueueClient queueClient = new QueueClientBuilder()
                .connectionString(connectionString)
                .queueName("ordersqueue")
                .buildClient();

        String base64 = Base64.getEncoder().encodeToString(orderJson.getBytes(StandardCharsets.UTF_8));
        queueClient.sendMessage(base64);
        log.info("Evento OrderCreated encolado en ordersqueue");
    }
}