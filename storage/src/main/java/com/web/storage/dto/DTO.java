package com.web.storage.dto;

public class DTO {

    private String name;

    private String type;

    private String brand;

    private Long rec_cost;


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

    public Long getRec_cost() {
        return rec_cost;
    }

    public void setRec_cost(Long rec_cost) {
        this.rec_cost = rec_cost;
    }
}
