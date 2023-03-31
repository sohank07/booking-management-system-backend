package com.se.bookingmanagementsystembackend.repository;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByHotelAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqualAndNumGuestsGreaterThanEqual(
            Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate, int numGuests);
}

