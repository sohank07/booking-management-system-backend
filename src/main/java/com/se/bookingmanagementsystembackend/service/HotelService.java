package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    List<Hotel> searchHotels(String location, LocalDate checkInTime, LocalDate checkOutDate, int numberOfGuests);

    List<Hotel> getAllHotels();

    List<Booking> getAllBookingsForHotel(int hotelId);

    List<Hotel> getHotelByCity(String city);
    Hotel saveHotel(Hotel hotel);
    void deleteHotel(int id);

    Hotel getHotelById(int id);
}
