package com.se.bookingmanagementsystembackend.repository;

import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByHotelNameContainingIgnoreCase(String hotelName);

    List<Hotel> findByCity(String city);

    List<Hotel> findByStateContainingIgnoreCase(String state);

    @Query("SELECT r.hotel FROM Room r WHERE r.hotel.city = :city AND r.hotel.available = true AND r.id NOT IN (SELECT res.room.id FROM Reservation res WHERE (res.checkInDate <= :checkOutDate AND res.checkOutDate >= :checkInDate)) GROUP BY r.hotel.hotelId HAVING COUNT(r.id) >= :numGuests")
    List<Hotel> searchAvailableHotels(@Param("city") String city, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate, @Param("numGuests") int numGuests);

    Optional<Hotel> findByHotelName(String hotelName);
}


