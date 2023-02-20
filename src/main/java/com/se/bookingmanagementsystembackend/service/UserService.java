package com.se.bookingmanagementsystembackend.service;

import com.se.bookingmanagementsystembackend.business.domain.user.User;
import com.se.bookingmanagementsystembackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    public User saveUser(User user){
        userRepository.save(user);
        return user;
    }

}
