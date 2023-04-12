package com.se.bookingmanagementsystembackend.apis.handler;

public class HotelAlreadyExistsException extends RuntimeException {
    public HotelAlreadyExistsException(String message) {
        super(message);
    }
}
