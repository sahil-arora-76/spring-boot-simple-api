package com.backend.api.api.repository;

import com.backend.api.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findUserByUsername(String username);
}
