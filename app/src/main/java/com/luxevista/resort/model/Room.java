package com.luxevista.resort.model;

public class Room {
    private String roomType;
    private String price;
    private String amenities;
    private int imageResourceId;

    public Room(String roomType, String price, String amenities, int imageResourceId) {
        this.roomType = roomType;
        this.price = price;
        this.amenities = amenities;
        this.imageResourceId = imageResourceId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
