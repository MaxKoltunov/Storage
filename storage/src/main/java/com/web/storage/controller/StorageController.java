package com.web.storage.controller;


import com.web.storage.dto.DTO;
import com.web.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/storage")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from storage!";
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody DTO dto) {
        storageService.addProduct(dto.getName(), dto.getType(), dto.getBrand(), dto.getRec_cost());
        return "A new product has been added to the storage";
    }
    // curl -X POST "http://localhost:8082/api/storage/add" -H "Content-Type: application/json" -d "{\"name\": \"test_product\", \"type\": \"test_type\", \"brand\": \"test_brand\", \"rec_cost\": 123}"

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestBody DTO dto) {
        storageService.deleteProduct(dto.getName(), dto.getType(), dto.getBrand(), dto.getRec_cost());
        return "Product has been deleted";
    }
    // curl -X DELETE "http://localhost:8082/api/storage/delete" -H "Content-Type: application/json" -d "{\"name\": \"test_product\", \"type\": \"test_type\", \"brand\": \"test_brand\", \"rec_cost\": 123}"
}
