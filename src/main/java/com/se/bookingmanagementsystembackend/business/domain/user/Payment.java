package com.se.bookingmanagementsystembackend.business.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private boolean paymentStatus;

    public Payment(Booking booking, BigDecimal amount, LocalDateTime paymentDate, boolean paymentStatus) {
        this.booking = booking;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public Payment() {

    }
}
