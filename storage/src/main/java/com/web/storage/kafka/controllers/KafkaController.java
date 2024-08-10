package com.web.storage.kafka.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.storage.entity.StorageProduct;
import com.web.storage.kafka.dto.KafkaStorageMessage;
import com.web.storage.kafka.dto.MessageDTO;
import com.web.storage.kafka.producers.KafkaProducerService;
import com.web.storage.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/storage")
public class KafkaController {

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private StorageRepository storageRepository;

    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageDTO messageDTO) {
        Optional<StorageProduct> storageProductOpt = storageRepository.findById(messageDTO.getId());
        if (storageProductOpt.isPresent()) {
            StorageProduct product = storageProductOpt.get();
            String name = product.getName();
            String type = product.getType();
            String brand = product.getBrand();
            Long cost;
            if (messageDTO.getCost() == null) {
                cost = product.getRec_cost();
            } else {
                cost = messageDTO.getCost();
            }
            KafkaStorageMessage storageMessage = new KafkaStorageMessage();
            storageMessage.setName(name);
            storageMessage.setType(type);
            storageMessage.setBrand(brand);
            storageMessage.setCost(cost);
            storageMessage.setArrivalDate(messageDTO.getArrivalDate());
            storageMessage.setDiscountId(messageDTO.getDiscountId());
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
            return "Message was sent";
        } else {
            return "There is no such product in the storage";
        }
    }

    // curl -X POST "http://localhost:8082/api/storage/send" -H "Content-Type: application/json" -d "{\"id\": 7, \"arrivalDate\": \"2024-08-10T08:00:00+05:00\", \"discountId\": null}"
    // curl -X POST "http://localhost:8082/api/storage/send" -H "Content-Type: application/json" -d "{\"id\": 7, \"cost\": 60, \"arrivalDate\": \"2024-08-10T08:00:00+05:00\", \"discountId\": null, \"amount\": 10}"
}
