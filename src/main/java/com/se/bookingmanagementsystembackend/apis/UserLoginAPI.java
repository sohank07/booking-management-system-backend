package com.se.bookingmanagementsystembackend.apis;

import com.se.bookingmanagementsystembackend.business.domain.user.User;
import com.se.bookingmanagementsystembackend.business.domain.user.UserLogin;
import com.se.bookingmanagementsystembackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/login/")
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "https://hoosierhotels.netlify.app/")
public class UserLoginAPI {
    private UserService userService;

    @PostMapping
    public User authenticateUser(@RequestBody @Valid UserLogin userLogin){
        System.out.println(userLogin.getEmail());
        System.out.println("Hello Sohan");
        return userService.loadUserByUsername(userLogin);
    }
}
