package com.web.storage.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "storage", schema = "storageschema")
public class StorageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_seq_gen")
    @SequenceGenerator(name = "storage_seq_gen", sequenceName = "storageschema.storage_id_seq", allocationSize = 1)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "recCost", nullable = false)
    private Long recCost;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getRecCost() {
        return recCost;
    }

    public void setRecCost(Long recCost) {
        this.recCost = recCost;
    }
}
