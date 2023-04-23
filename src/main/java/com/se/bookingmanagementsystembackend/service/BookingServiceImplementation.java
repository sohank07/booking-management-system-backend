package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import com.se.bookingmanagementsystembackend.business.domain.user.User;
import com.se.bookingmanagementsystembackend.repository.BookingRepository;
import com.se.bookingmanagementsystembackend.repository.HotelRepository;
import com.se.bookingmanagementsystembackend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class BookingServiceImplementation implements BookingService{

    private BookingRepository bookingRepository;

    private HotelRepository hotelRepository;

    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PaymentServiceImplementation paymentService;

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
    public ResponseEntity<String> createBooking(Booking booking, int hotelId) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);

        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            int availableRooms = hotel.getNumRooms() - (int)Math.ceil(booking.getNumGuests()/2.0);
            System.out.println(availableRooms);
            if (availableRooms >= 0) {
                hotel.setNumRooms(availableRooms);
            }
            if (availableRooms == 0) {
                hotel.setAvailable(false);
            }

            bookingRepository.save(booking);

            LocalDateTime currentDate = LocalDateTime.now();
            BigDecimal amount = BigDecimal.valueOf(hotel.getPriceRange() * (int)Math.ceil(booking.getNumGuests()/2.0));
            paymentService.createPayment(booking, amount, currentDate, true);
            Long userId = booking.getUser().getId();

            List<String> userDetails = getUserDetails(userId);
            String userEmail = userDetails.get(0);
            String userName = userDetails.get(1);

            String emailSubject = "Booking Confirmation";
            LocalDate checkInDate = booking.getCheckInDate();
            LocalDate checkOutDate = booking.getCheckOutDate();
            int numberOfGuests = booking.getNumGuests();
            Long bookingConfirmationId = booking.getId();
            String emailBody = "Dear " + userName + ",\n\nYour booking has been confirmed. Please find the booking details attached with this mail.\n\n Booking Confirmation ID: "+bookingConfirmationId+"\nCheck-in Date: "+checkInDate+"\nCheck-out Date: "+checkOutDate+"\nNumber of Guests: "+numberOfGuests;
            emailService.sendBookingConfirmation(userEmail, emailSubject, emailBody);
            return ResponseEntity.ok("Booking confirmed and email sent.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private List<String> getUserDetails(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        List<String> userDetails = new ArrayList<>();
        userDetails.add(user.get().getEmail());
        userDetails.add(user.get().getFirstName());
        return userDetails;
    }

    @Override
    public ResponseEntity<String> guestCheckOut(Booking booking, int hotelId) {
        System.out.println(hotelId);
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        System.out.println("Hi");
        if (optionalHotel.isPresent()) {
            LocalDate checkOutDate = booking.getCheckOutDate();
            int roomsOccupied = (int)Math.ceil(booking.getNumGuests()/2.0);

            int updatedAvailableRooms = optionalHotel.get().getNumRooms() + roomsOccupied;
            optionalHotel.get().setNumRooms(updatedAvailableRooms);
            if (optionalHotel.get().getAvailable() == false){
                optionalHotel.get().setAvailable(true);
            }
            bookingRepository.delete(booking);
            return ResponseEntity.ok("Rooms marked as available after checkout.");
        } else {
            System.out.println("Not Found!!!");
            return ResponseEntity.notFound().build();
        }
    }
}
