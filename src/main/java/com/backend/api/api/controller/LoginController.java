package com.backend.api.api.controller;

import com.backend.api.api.model.User;
import com.backend.api.api.service.UserService;
import com.backend.api.api.util.ApiResponse;
import com.backend.api.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    JwtUtil util;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            if (user.username == null || user.password == null) {
                apiResponse.setMessage("Password and username must be provided");
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }

            Optional<User> isUser = userService.findByUsername(user.username);
            if (isUser.isEmpty()) {
                apiResponse.setMessage("no user found");
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
            User foundUser = isUser.get();
            if (!Objects.equals(foundUser.password, user.password)) {
                apiResponse.setMessage("Incorrect password");
                return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
            }
            apiResponse.setMessage("Ok");
            String token = util.generateToken(foundUser);
            apiResponse.setData(token);
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage().isEmpty() ? "Internal server error": e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
