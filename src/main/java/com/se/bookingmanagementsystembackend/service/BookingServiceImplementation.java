package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import com.se.bookingmanagementsystembackend.repository.BookingRepository;
import com.se.bookingmanagementsystembackend.repository.HotelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImplementation implements BookingService{

    private BookingRepository bookingRepository;

    private HotelRepository hotelRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Booking> findBookings(Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate, int numGuests) {
        return bookingRepository.findByHotelAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqualAndNumGuestsGreaterThanEqual(
                hotel, checkOutDate, checkInDate, numGuests);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void createBooking(Booking booking, int hotelId) {
        Hotel hotel = entityManager.find(Hotel.class, hotelId);
        entityManager.persist(booking);
    }

    @Override
    public ResponseEntity<Booking> createBooking(int hotelId, Booking booking) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            booking.setHotel(hotel);
            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.ok(savedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
