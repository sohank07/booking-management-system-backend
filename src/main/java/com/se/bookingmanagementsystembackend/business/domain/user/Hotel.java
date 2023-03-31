package com.se.bookingmanagementsystembackend.business.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;

    @Column(name = "hotel_name")
    private String hotelName;

    private String address;

    private String city;

    private String state;

    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String website;

    @Column(name = "num_rooms")
    private int numRooms;

    @ElementCollection
    @CollectionTable(name = "room_types")
    @Column(name = "room_type")
    private List<String> roomTypes;

    @ElementCollection
    @CollectionTable(name = "amenities")
    @Column(name = "amenity")
    private List<String> amenities;

    @Column(name = "price_range")
    private double priceRange;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    private double rating;

    private boolean available;

    public boolean getAvailable() {
        return available;
    }
}
