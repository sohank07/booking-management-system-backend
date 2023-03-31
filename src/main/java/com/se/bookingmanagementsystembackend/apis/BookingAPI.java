package com.se.bookingmanagementsystembackend.apis;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import com.se.bookingmanagementsystembackend.repository.BookingRepository;
import com.se.bookingmanagementsystembackend.repository.HotelRepository;
import com.se.bookingmanagementsystembackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            int availableRooms = hotel.getNumRooms() - (int)Math.ceil(booking.getNumGuests()/2.0);
            System.out.println(availableRooms);
            if (availableRooms >= 0) {
                hotel.setNumRooms(availableRooms);
                if (availableRooms == 0) {
                    hotel.setAvailable(false);
                }
                hotelRepository.save(hotel);
                Booking savedBooking = bookingRepository.save(booking);
                return ResponseEntity.ok("Booking Confirmed");
            } else {
                return ResponseEntity.badRequest().body("Insufficient rooms available");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
