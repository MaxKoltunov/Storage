package com.web.storage.service;


import com.web.storage.entity.StorageProduct;
import com.web.storage.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Cacheable(value = "storage", key = "#name + #type + #brand + #rec_cost")
    public StorageProduct addProduct(String name, String type, String brand, Long rec_cost) {
        StorageProduct storageProduct = new StorageProduct();
        storageProduct.setName(name);
        storageProduct.setType(type);
        storageProduct.setBrand(brand);
        storageProduct.setRec_cost(rec_cost);
        return storageRepository.save(storageProduct);
    }

    @Cacheable(value = "storage", key = "#name + #type + #brand + #rec_cost")
    public void deleteProduct(String name, String type, String brand, Long rec_cost) {
        storageRepository.deleteProduct(name, type, brand, rec_cost);
    }

}
