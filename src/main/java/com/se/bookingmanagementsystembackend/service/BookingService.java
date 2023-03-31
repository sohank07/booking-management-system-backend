package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Booking> findBookings(Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate, int numGuests);
    List<Booking> findAll();

    @Transactional
    public void createBooking(Booking booking, int hotelId);

    public ResponseEntity<Booking> createBooking(int hotelId, Booking booking);
}

