package com.web.storage.service;


import com.web.storage.entity.StorageProduct;
import com.web.storage.repository.StorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageService {

    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Cacheable(value = "storage", key = "#name + #type + #brand + #rec_cost")
    public StorageProduct addProduct(String name, String type, String brand, Long recCost) {
        log.info("addProduct() - starting");
        StorageProduct storageProduct = StorageProduct.builder()
                .name(name)
                .type(type)
                .brand(brand)
                .recCost(recCost)
                .build();
        log.info("A new product has been added to the storage");
        return storageRepository.save(storageProduct);
    }

    @Cacheable(value = "storage", key = "#name + #type + #brand + #rec_cost")
    public void deleteProduct(String name, String type, String brand, Long rec_cost) {
        storageRepository.deleteProduct(name, type, brand, rec_cost);
        log.info("Product has been deleted");
    }

}
