package com.backend.api.api.service;

import com.backend.api.api.model.User;
import com.backend.api.api.repository.UserRepository;
import com.mongodb.MongoClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(User user) throws Exception, MongoClientException {
       return userRepository.insert(user);
    }

    public Optional<User> findByUsername(String username) throws Exception, MongoClientException {
        return userRepository.findUserByUsername(username);
    }
}
