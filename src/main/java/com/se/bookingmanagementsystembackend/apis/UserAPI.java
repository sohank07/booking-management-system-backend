package com.se.bookingmanagementsystembackend.apis;

import com.se.bookingmanagementsystembackend.business.domain.user.User;
import com.se.bookingmanagementsystembackend.repository.UserRepository;
import com.se.bookingmanagementsystembackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users/")
public class UserAPI {

    private UserService userService;
    private UserRepository userRepository;
    @GetMapping
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @PostMapping
    public User registerUser(@RequestBody @Valid User user){
        System.out.println(user);
        return userService.saveUser(user);
    }

    @PostMapping("update/")
    public User updateUser(@RequestBody @Valid User user){
        return userService.updateUser(user);
    }

}

