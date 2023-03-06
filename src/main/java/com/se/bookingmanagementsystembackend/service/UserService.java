package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.User;
import com.se.bookingmanagementsystembackend.business.domain.user.UserLogin;
import com.se.bookingmanagementsystembackend.repository.UserRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
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
        } else if (authenticateUser.get().getPassword().equals(userLogin.getPassword())){
            Optional<User> userOptional = userRepository.findByEmail(userLogin.getEmail());
            return userOptional.get();
        } else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Password");
        }
    }

    public User updateUser(User user) {
        if(user.getId() != null){
            return userRepository.save(user);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found!");
        }
    }
}
