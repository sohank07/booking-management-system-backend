package com.se.bookingmanagementsystembackend.apis;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import com.se.bookingmanagementsystembackend.repository.BookingRepository;
import com.se.bookingmanagementsystembackend.repository.HotelRepository;
import com.se.bookingmanagementsystembackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/hotels")
public class BookingAPI {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private BookingRepository bookingRepository;


    @GetMapping("/bookings")
    public List<Booking> findAll(){
        return bookingService.findAll();
    }

    @GetMapping("/bookings/search")
    public List<Booking> searchBookings(@RequestParam("hotelId") int hotelId,
                                        @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                        @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                        @RequestParam("numGuests") int numGuests) {
        Hotel hotel = new Hotel();
        hotel.setHotelId(hotelId);
        return bookingService.findBookings(hotel, checkInDate, checkOutDate, numGuests);
    }

    @PostMapping("/{hotelId}/bookings")
    public ResponseEntity<String> bookHotel(@PathVariable int hotelId, @RequestBody Booking booking) {
        try {
            return bookingService.createBooking(booking, hotelId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insufficient rooms available");
        }
    }

    @PostMapping("/{hotelId}/bookings/check-out")
    public ResponseEntity<String> guestCheckOut(@RequestBody Booking booking, @PathVariable int hotelId) {
        try {
            bookingService.guestCheckOut(booking, hotelId);
            return ResponseEntity.ok("Rooms marked available after successfull check-out!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while checking out");
        }
    }

}
