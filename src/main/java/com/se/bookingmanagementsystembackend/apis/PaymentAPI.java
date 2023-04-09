//package com.se.bookingmanagementsystembackend.apis;
//
//import com.se.bookingmanagementsystembackend.business.domain.user.Payment;
//import com.se.bookingmanagementsystembackend.business.domain.user.PaymentStatus;
//import com.se.bookingmanagementsystembackend.repository.PaymentRepository;
//import com.se.bookingmanagementsystembackend.service.PaymentService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/payments")
//public class PaymentAPI {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @PostMapping
//    public Payment createPayment(@RequestBody Payment payment){
//        return paymentService.createPayment(payment);
//    }
//
//    @PatchMapping("/{paymentId}")
//    public Payment updatePaymentStatus(@PathVariable Long paymentId, PaymentStatus paymentStatus){
//        return paymentService.updatePaymentStatus(paymentId, paymentStatus);
//    }
//
//    @GetMapping("/{paymentId}")
//    public Payment getPaymentById(@PathVariable Long paymentId) {
//        return paymentService.getPaymentById(paymentId);
//    }
//}
