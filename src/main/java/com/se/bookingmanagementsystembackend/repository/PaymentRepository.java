package com.se.bookingmanagementsystembackend.repository;

import com.se.bookingmanagementsystembackend.business.domain.user.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}