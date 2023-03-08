package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.User;
import com.se.bookingmanagementsystembackend.business.domain.user.UserLogin;
import com.se.bookingmanagementsystembackend.configuration.PasswordEncoding;
import com.se.bookingmanagementsystembackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoding passwordEncoding;

    public User saveUser(User user) {

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        String hashedPassword = passwordEncoding.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        if (!userOptional.isPresent()) {
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Registered");
        }
    }

    public User loadUserByUsername(UserLogin userLogin){
        Optional<User> authenticateUser = userRepository.findByEmail(userLogin.getEmail());
        System.out.println("Hello from Service!!");
        System.out.println(authenticateUser);

        if (!authenticateUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        } else if (passwordEncoding.matches(userLogin.getPassword(), authenticateUser.get().getPassword())) {
            //Optional<User> userOptional = userRepository.findByEmail(userLogin.getEmail());
            return authenticateUser.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Password");
        }
    }

    public User updateUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist.");
        } else {
            Long userId = existingUser.get().getId();
            String hashedPassword = passwordEncoding.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            user.setId(userId);
            return userRepository.save(user);
        }
    }
}
