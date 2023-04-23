package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Hotel;
import com.se.bookingmanagementsystembackend.apis.handler.HotelAlreadyExistsException;
import com.se.bookingmanagementsystembackend.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HotelServiceImplementation implements HotelService{


    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> searchHotels(String location, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests) {

        List<Hotel> availableHotels = new ArrayList<>();
        List<Hotel> hotels = hotelRepository.findAll();
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equalsIgnoreCase(location) && hotel.getAvailable()) {
                boolean isAvailable = true;
                for (Booking booking : hotel.getBookings()) {
                    if (checkInDate.isBefore(booking.getCheckOutDate()) && checkOutDate.isAfter(booking.getCheckInDate())) {
                        isAvailable = false;
                        break;
                    }
                }
                if (isAvailable && hotel.getNumRooms() >= numberOfGuests) {
                    availableHotels.add(hotel);
                }
            }
        }

        return availableHotels;


        //        List<Hotel> availableHotels = hotelRepository.findByCity(location);
//
//        availableHotels = availableHotels.stream()
//                .filter(hotel -> hotel.getAvailable())
//                .filter(hotel -> hotel.getNumRooms() >= numberOfGuests)
//                .collect(Collectors.toList());
//        return availableHotels;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Booking> getAllBookingsForHotel(int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));
        return hotel.getBookings();
    }

    @Override
    public List<Hotel> getHotelByCity(String city) {
        return hotelRepository.findByCity(city);
    }

    public Hotel saveHotel(Hotel hotel) {
        Optional<Hotel> existingHotel = hotelRepository.findByHotelName(hotel.getHotelName());
        if (existingHotel.isPresent()) {
            throw new HotelAlreadyExistsException("Hotel with name " + hotel.getHotelName() + " already exists");
        }
        // Save the hotel if it doesn't already exist
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(int hotelId) {
        // Check if the hotel exists
        if (!hotelRepository.existsById(hotelId)) {
            throw new HotelAlreadyExistsException("Hotel with ID " + hotelId + " not found");
        }
        // Delete the hotel if it exists
        hotelRepository.deleteById(hotelId);
    }

    @Override
    public Hotel getHotelById(int id) {
        return hotelRepository.findById(id).orElse(null);
    }

}
