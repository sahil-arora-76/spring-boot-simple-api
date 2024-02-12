package com.backend.api.api.controller;

import com.backend.api.api.model.User;
import com.backend.api.api.repository.UserRepository;
import com.backend.api.api.service.UserService;
import com.backend.api.api.util.ApiResponse;
import com.backend.api.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        ApiResponse response = new ApiResponse();
        try {
            if (user.password == null || user.username == null) {
                response.setMessage("Password and username must be provided");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Optional<User> isUser = userService.findByUsername(user.username);
            if (isUser.isPresent()) {
                response.setMessage("User with this username already exists");
                return new ResponseEntity<>(response, HttpStatus.FOUND);
            }
            User createdUser = userService.createUser(user);
            response.setData(createdUser);
            response.setMessage("Ok");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            response.setMessage(e.getMessage().isEmpty() ? "Internal server error": e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
