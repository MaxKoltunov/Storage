package com.web.storage.kafka.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.storage.entity.StorageProduct;
import com.web.storage.exceptions.EmptyStorageException;
import com.web.storage.exceptions.ObjectNotFoundException;
import com.web.storage.kafka.dto.KafkaStorageMessage;
import com.web.storage.kafka.dto.MessageDTO;
import com.web.storage.kafka.producers.KafkaProducerService;
import com.web.storage.repository.StorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/storage")
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private StorageRepository storageRepository;


    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageDTO messageDTO) {
        log.info("sendMessage() - starting");
        Optional<StorageProduct> storageProductOpt = storageRepository.findById(messageDTO.getId());
        if (storageProductOpt.isPresent()) {
            StorageProduct product = storageProductOpt.get();
            String name = product.getName();
            String type = product.getType();
            String brand = product.getBrand();
            Long cost;
            if (messageDTO.getCost() == null) {
                cost = product.getRecCost();
            } else {
                cost = messageDTO.getCost();
            }
            KafkaStorageMessage storageMessage = KafkaStorageMessage.builder()
                    .name(name)
                    .type(type)
                    .brand(brand)
                    .cost(cost)
                    .arrivalDate(messageDTO.getArrivalDate())
                    .discountId(messageDTO.getDiscountId())
                    .build();
            if (messageDTO.getAmount() != null) {
                storageMessage.setAmount(messageDTO.getAmount());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String message =  objectMapper.writeValueAsString(storageMessage);
                System.out.println(message);
                producerService.sendMessage("storage-topic", message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
           log.info("Message was sent");
        } else {
            throw new ObjectNotFoundException("There is no such product in the storage");
        }
    }

    // curl -X POST "http://localhost:8082/api/storage/send" -H "Content-Type: application/json" -d "{\"id\": 7, \"arrivalDate\": \"2024-08-10T08:00:00+05:00\", \"discountId\": null}"
    // curl -X POST "http://localhost:8082/api/storage/send" -H "Content-Type: application/json" -d "{\"id\": 7, \"cost\": 60, \"arrivalDate\": \"2024-08-10T08:00:00+05:00\", \"discountId\": null, \"amount\": 10}"

    @Scheduled(fixedRate = 60000)
    public void sendScheduledMessage() {
        log.info("sendScheduledMessage() - starting");

        List<Long> storageIds = storageRepository.getAllIds();

        if (storageIds.isEmpty()) {
            throw new EmptyStorageException("There are no products in the storage");
        }

        Random random = new Random();

        Long randomId = storageIds.get(random.nextInt(storageIds.size()));

        Optional<StorageProduct> productOpt = storageRepository.findById(randomId);

        if (productOpt.isEmpty()) {
            throw new ObjectNotFoundException("There is no such product in the storage");
        }

        StorageProduct product = productOpt.get();

        ZoneId zoneId = ZoneId.of("UTC+05:00");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        Timestamp arrivalDate = Timestamp.valueOf(localDateTime);

        KafkaStorageMessage kafkaStorageMessage = KafkaStorageMessage.builder()
                .name(product.getName())
                .type(product.getType())
                .brand(product.getBrand())
                .cost(product.getRecCost() + random.nextLong(31) - 15)
                .arrivalDate(arrivalDate)
                .amount(random.nextLong(100))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(kafkaStorageMessage);
            System.out.println(message);
            producerService.sendMessage("storage-topic", message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("Message was sent");
    }
}
