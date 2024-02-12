package com.backend.api.api.controller;

import com.backend.api.api.model.User;
import com.backend.api.api.util.ApiResponse;
import com.backend.api.api.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
    @Autowired
    JwtUtil jwt;

    @GetMapping
    public ApiResponse getHello(HttpServletRequest request) {
        ApiResponse response = new ApiResponse();
        User user = (User) request.getAttribute("user");
        if (user == null) {
            response.setMessage("No user found login again");
            return response;
        }
        response.setData(user);
        response.setMessage("Hello user " + user.getUsername());
        return response;
    }

}
