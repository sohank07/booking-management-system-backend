package com.se.bookingmanagementsystembackend.apis;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import com.se.bookingmanagementsystembackend.apis.handler.HotelAlreadyExistsException;
import com.se.bookingmanagementsystembackend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/hotels")
@CrossOrigin(origins = "http://localhost:3000")
public class HotelAPI {

    @Autowired
    private HotelService hotelService;

    // GET all hotels
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    // GET hotel by City
    @GetMapping("/{city}")
    public List<Hotel> getHotelByCity(@PathVariable String city) {
        return hotelService.getHotelByCity(city);
    }

    // POST create new hotel
    @PostMapping
    public ResponseEntity<String> createHotel(@RequestBody Hotel hotel) {
        try {
            hotelService.saveHotel(hotel);
            return ResponseEntity.ok("Hotel created successfully");
        } catch (HotelAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // PUT update existing hotel
    @PutMapping("/{id}")
    public ResponseEntity<String> updateHotel(@PathVariable int id, @RequestBody Hotel hotel) {
        if (hotelService.getHotelById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        hotel.setHotelId(id);
        hotelService.saveHotel(hotel);
        return ResponseEntity.ok("Hotel updated successfully");
    }

    // DELETE hotel by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable int id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok("Hotel deleted successfully");
    }

    @GetMapping("/search")
    public List<Hotel> searchHotels(@RequestParam String location,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                                    @RequestParam int numberOfGuests) {
        return hotelService.searchHotels(location, checkInDate, checkOutDate, numberOfGuests);
    }

    @GetMapping("/{hotelId}/bookings")
    public List<Booking> getAllBookingsForHotel(@PathVariable int hotelId) {
        return hotelService.getAllBookingsForHotel(hotelId);
    }
//    public ResponseEntity<List<Booking>> findBookingsByHotel(@PathVariable int hotelId) {
//        Optional<Hotel> optionalHotel = Optional.ofNullable(hotelService.getHotelById(hotelId));
//        if (optionalHotel.isPresent()) {
//            Hotel hotel = optionalHotel.get();
//            List<Booking> bookings = hotel.getBookings();
//            return ResponseEntity.ok(bookings);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
