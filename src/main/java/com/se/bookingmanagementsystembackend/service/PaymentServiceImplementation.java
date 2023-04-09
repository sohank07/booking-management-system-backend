package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.Booking;
import com.se.bookingmanagementsystembackend.business.domain.user.Payment;
import com.se.bookingmanagementsystembackend.handler.ResourceNotFoundException;
import com.se.bookingmanagementsystembackend.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class PaymentServiceImplementation implements PaymentService{

    private PaymentRepository paymentRepository;


    @Override
    public Payment createPayment(Booking booking, BigDecimal amount, LocalDateTime paymentDate, boolean paymentStatus) {
        Payment payment = new Payment(booking, amount, paymentDate, paymentStatus);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePaymentStatus(Long paymentId, boolean status) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
        return payment;
    }
}
