package com.se.bookingmanagementsystembackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendBookingConfirmation(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("hoosierhotels@gmail.com"); // Update with the appropriate "from" address

            // Auto-wire javaMailSender bean in your Spring Boot application
            // and use it to send the email
            javaMailSender.send(message);
        } catch (RuntimeException ex) {
            // Handle exceptions that may occur during email sending
            // For example, log the error or throw a custom exception
            ex.printStackTrace();
            // throw new CustomException("Failed to send email", ex);
        }
    }
}