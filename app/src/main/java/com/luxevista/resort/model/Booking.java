package com.luxevista.resort.model;

public class Booking {
    private int bookingId;
    private String bookingType;
    private String bookingDetails;

    public Booking(int bookingId, String bookingType, String bookingDetails) {
        this.bookingId = bookingId;
        this.bookingType = bookingType;
        this.bookingDetails = bookingDetails;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getBookingType() {
        return bookingType;
    }

    public String getBookingDetails() {
        return bookingDetails;
    }
}
