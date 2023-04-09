package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentService {

    public Payment createPayment(Booking booking, BigDecimal amount, LocalDateTime paymentDate, boolean paymentStatus);

    public Payment updatePaymentStatus(Long paymentId, boolean status);

    public Payment getPaymentById(Long paymentId);
}
