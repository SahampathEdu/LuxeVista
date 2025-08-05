package com.luxevista.resort.model;

public class Service {
    private String serviceName;
    private String price;
    private String description;

    public Service(String serviceName, String price, String description) {
        this.serviceName = serviceName;
        this.price = price;
        this.description = description;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
